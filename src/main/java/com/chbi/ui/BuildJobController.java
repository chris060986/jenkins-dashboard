package com.chbi.ui;

import com.chbi.ApplicationConfiguration;
import com.chbi.json.entities.JenkinsBuild;
import com.chbi.json.entities.JenkinsJob;
import com.chbi.rest.DataProvider;
import com.chbi.ui.entities.BuildBox;
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

    private DataProvider dataProvider;
    private ApplicationConfiguration configuration;

    @Autowired
    BuildJobController(DataProvider productService, ApplicationConfiguration config) {
        this.dataProvider = productService;
        this.configuration = config;
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

    private BuildBox createBuildBox(JenkinsJob job) {
        JenkinsBuild lastBuild = dataProvider.getLastBuild(job);
        String changingUsers = dataProvider.getChangingUserFor(lastBuild.getUrl());

        return new BuildBox().withBranchname(job.getName())
                .withBuildNumber(lastBuild.getNumber()).withBuildUrl(job.getUrl())
                .withCulprits(changingUsers).withColor(job.getColor()).withJiraTicket(calculateHeadline(job));
    }

    private String calculateHeadline(JenkinsJob job){
        String headLine = "";
        String url = job.getUrl();
        if(url.contains(SLASH + MASTER + SLASH)){
            headLine = MASTER;
        }else if((url.contains(SLASH + SNAPSHOT + SLASH))){
            headLine = SNAPSHOT;
        } else if((url.contains("--" + SCHEDULE))){
            headLine = getMatchingPart(SCHEDULE_REGEX, url);
        } else {
            headLine = getMatchingPart(configuration.getJiraTaskRegEx(), url);
        }
        if(Strings.isNullOrEmpty(headLine)){
            return job.getName();
        }
        return headLine;
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