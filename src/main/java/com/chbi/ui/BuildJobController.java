package com.chbi.ui;

import com.chbi.ApplicationConfiguration;
import com.chbi.json.entities.JenkinsBuildInstance;
import com.chbi.json.entities.JenkinsBuildPipeline;
import com.chbi.json.entities.JenkinsJob;
import com.chbi.rest.DataProvider;
import com.chbi.rest.UrlRewriter;
import com.chbi.ui.entities.BuildBox;
import com.chbi.ui.entities.JobColor;
import com.chbi.ui.entities.Swimlane;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BuildJobController {

    private static final String MASTER = "master";
    private static final String SNAPSHOT = "snapshot";
    private static final String SCHEDULE = "schedule";
    private static final String SCHEDULE_REGEX = SCHEDULE + "-\\d{1,3}";
    private static final String SLASH = "/";
    private static final String FEATURE_REGEX = "^feature\\/.*";
    private static final String BUGFIX_REGEX = "^bugfix\\/.*";
    private static final String FEATURE = "feature";
    private static final String BUGFIX = "bugfix";

    private DataProvider dataProvider;
    private ApplicationConfiguration configuration;
    private UrlRewriter urlRewriter;

    @Autowired
    BuildJobController(DataProvider productService, ApplicationConfiguration config, UrlRewriter urlRewriter) {
        this.dataProvider = productService;
        this.configuration = config;
        this.urlRewriter = urlRewriter;
    }

    @RequestMapping("/jenkinsJobs")
    public String jenkinsBuildJobs(Model model) {
        List<BuildBox> builds = new ArrayList<>();
        List<JenkinsJob> jenkinsJobs = dataProvider.getJenkinsJobs();
        for (JenkinsJob job : jenkinsJobs) {
            builds.add(createBuildBox(job));
        }

        model.addAttribute("jenkinsJobs", builds);

        return "jenkinsJobs";
    }

    @RequestMapping("/swimlanes")
    public String displaySwimlanes(Model model) {
        List<Swimlane> swimlanes = new ArrayList<>();
        List<JenkinsJob> jenkinsJobs = dataProvider.getJenkinsJobs();

        Swimlane mainline = new Swimlane().withHeadline("Mainline");
        Swimlane integration = new Swimlane().withHeadline("Integration");
        Swimlane misc = new Swimlane().withHeadline("Miscellaneous");
        Swimlane deployment = new Swimlane().withHeadline("Deployment");

        for (JenkinsJob job : jenkinsJobs) {
            BuildBox box = createBuildBox(job);
            String branchName = box.getBranchName();

            if (branchName.contains("master") || branchName.contains("snapshot")) {
                mainline.withBuildBoxes(box);
            } else if (branchName.contains("schedule")) {
                integration.withBuildBoxes(box);
            } else if (branchName.contains("deployment")) {
                deployment.withBuildBoxes(box);
            } else {
                misc.withBuildBoxes(box);
            }
        }

        swimlanes.add(mainline);
        swimlanes.add(integration);
        swimlanes.add(misc);
        swimlanes.add(deployment);

        model.addAttribute("swimlanes", swimlanes);

        return "swimlanes";
    }

    private BuildBox createBuildBox(JenkinsJob job) {
        JenkinsBuildPipeline pipeline = dataProvider.getJenkinsBuildPipeline(job);

        JenkinsBuildInstance buildInstance = dataProvider.getBuildInstance(pipeline.getLastBuild().getUrl());
        String changingUsers = dataProvider.getChangingUserFor(buildInstance);

        BuildBox buildBox = new BuildBox()
                .withDisplayName(buildInstance.getFullDisplayName())
                .withBranchName(urlRewriter.decodeStringInUtf8(job.getName()))
                .withBranchType(getBranchType(job))
                .withBuildNumber(pipeline.getLastBuild().getNumber())
                .withBuildUrl(job.getUrl())
                .withCulprits(changingUsers)
                .withColor(JobColor.valueOf(job.getColor()))
                .withJiraTicket(parseJiraTicket(job));
        if(!Strings.isNullOrEmpty(buildBox.getJiraTicket())){
            buildBox = buildBox.withJiraUrl(urlRewriter.prepareJiraUrl(buildBox.getJiraTicket()));
        }

        return buildBox;
    }

    private String parseJiraTicket(JenkinsJob job){
        return getMatchingPart(configuration.getJiraTaskRegEx(), job.getUrl());
    }

    private String getBranchType(JenkinsJob job){
        String branchType = "";

        String name = urlRewriter.decodeStringInUtf8(job.getName());
        String url = job.getUrl();
        if(url.contains(SLASH + MASTER + SLASH)){
            branchType = MASTER;
        }else if((url.contains(SLASH + SNAPSHOT + SLASH))){
            branchType = SNAPSHOT;
        } else if((url.contains("--" + SCHEDULE))){
            branchType = getMatchingPart(SCHEDULE_REGEX, url);
        } else if(name.matches(FEATURE_REGEX)) {
            branchType = FEATURE;
        } else if(name.matches(BUGFIX_REGEX)) {
            branchType = BUGFIX;
        }
        return branchType;
    }

    private String getMatchingPart(String regex, String url) {
        String headLine = "";
        if(regex!=null){
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find())
            {
                headLine = matcher.group(0);
            }
        }
        return headLine;
    }


}