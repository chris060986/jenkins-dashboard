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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Controller
public class BuildJobController {

    private static final String MASTER = "master";
    private static final String SNAPSHOT = "snapshot";
    private static final String SCHEDULE = "schedule";
    private static final String SCHEDULE_REGEX = SCHEDULE + "-\\d{1,3}";
    private static final String SLASH = "/";
    private static final String FEATURE_REGEX = "^feature/.*";
    private static final String BUGFIX_REGEX = "^bugfix/.*";
    private static final String FEATURE = "feature";
    private static final String BUGFIX = "bugfix";

    private DataProvider dataProvider;
    private ApplicationConfiguration configuration;
    private UrlRewriter urlRewriter;
    private GifProvider gifProvider;
    private boolean isFail;


    @Autowired
    BuildJobController(DataProvider productService, ApplicationConfiguration config, UrlRewriter urlRewriter, GifProvider gifProvider) {

        this.dataProvider = productService;
        this.configuration = config;
        this.urlRewriter = urlRewriter;
        this.gifProvider = gifProvider;
    }

    @RequestMapping("/jenkinsJobs")
    public String jenkinsBuildJobs(Model model) {
        List<JenkinsJob> jenkinsJobs = dataProvider.getJenkinsJobs();

        model.addAttribute("jenkinsJobs", getBoxesFor(jenkinsJobs));

        return "jenkinsJobs";
    }


    @RequestMapping("/swimlanes")
    public String displaySwimlanes(Model model) {
        List<Swimlane> swimlanes = new ArrayList<>();
        List<JenkinsJob> jenkinsJobs = dataProvider.getJenkinsJobs();
        List<BuildBox> boxes = getBoxesFor(jenkinsJobs);

        for (String swimlaneKey : configuration.getSwimlanes().keySet()) {
            String regExp = configuration.getSwimlanes().get(swimlaneKey);
            Swimlane lane = new Swimlane().withHeadline(swimlaneKey);
            lane.withBuildBoxes(getMatchingBoxes(boxes, regExp));
            swimlanes.add(lane);
        }

        model.addAttribute("swimlanes", swimlanes);

        isFail = isMainlineRed(swimlanes);
        boolean showGif = isFail || isAllGreen(swimlanes);
        model.addAttribute("showGif", true);

        return "swimlanes";
    }

    @RequestMapping(value = "image/gif")
    @ResponseBody
    public byte[] getImage()  {
        try {
            File imageFile = gifProvider.getRandomGifAsFile(isFail);
            return Files.readAllBytes(imageFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private List<BuildBox> getMatchingBoxes(List<BuildBox> boxes, String regExp) {
        return boxes.stream().filter(buildBox -> buildBox.getDisplayName().matches(regExp)).collect(toList());
    }

    private List<BuildBox> getBoxesFor(List<JenkinsJob> jenkinsJobs) {
        return jenkinsJobs.parallelStream()
                .filter(BuildJobController::isNoMultiBranchProject)
                .map(this::createBuildBox)
                .collect(toList());
    }

    private BuildBox createBuildBox(JenkinsJob job) {
        JenkinsBuildPipeline pipeline = dataProvider.getJenkinsBuildPipeline(job);
        BuildBox buildBox = new BuildBox();

        if (pipeline != null && pipeline.getLastBuild() != null) {
            JenkinsBuildInstance buildInstance = dataProvider.getBuildInstance(pipeline.getLastBuild().getUrl());
            String changingUsers = dataProvider.getChangingUserFor(buildInstance);

            buildBox.withDisplayName(buildInstance.getFullDisplayName())
                    .withCulprits(changingUsers)
                    .withBuildNumber(pipeline.getLastBuild().getNumber());

        } else {
            String displayName = pipeline != null ? pipeline.getDisplayName() : "not even build-pipeline";
            buildBox.withDisplayName(displayName)
                    .withCulprits(DataProvider.getUnknownUser())
                    .withBuildNumber(0);
        }

        buildBox.withBranchName(urlRewriter.decodeStringInUtf8(job.getName()))
                .withBranchType(getBranchType(job))
                .withBuildUrl(job.getUrl())
                .withColor(JobColor.valueOf(job.getColor()))
                .withJiraTicket(parseJiraTicket(job));
        if (!Strings.isNullOrEmpty(buildBox.getJiraTicket())) {
            buildBox = buildBox.withJiraUrl(urlRewriter.prepareJiraUrl(buildBox.getJiraTicket()));
        }

        return buildBox;
    }

    private String parseJiraTicket(JenkinsJob job) {
        return getMatchingPart(configuration.getJiraTaskRegEx(), job.getUrl());
    }

    private boolean isAllGreen(List<Swimlane> swimlanes) {
        for (Swimlane lane : swimlanes) {
            boolean nonGreenPresent = lane.getBuildBoxes().stream().anyMatch(isNotGreen());
            if (nonGreenPresent) {
                return false;
            }
        }

        return true;
    }

    private boolean isMainlineRed(List<Swimlane> swimlanes) {
        if (swimlanes != null && swimlanes.size() >= 1) {
            Swimlane main = swimlanes.get(0);
            return main.getBuildBoxes().stream().anyMatch(isRed());
        }
        return false;
    }

    /*for UT */ Predicate<BuildBox> isNotGreen() {
        return input -> JobColor.blue != input.getColor() && JobColor.blue_anime != input.getColor();
    }

    private Predicate<BuildBox> isRed() {
        return input -> JobColor.red.equals(input.getColor()) || JobColor.red_anime.equals(input.getColor());
    }

    private static boolean isNoMultiBranchProject(JenkinsJob jenkinsJob) {
        return !jenkinsJob.get_class().contains("WorkflowMultiBranchProject");
    }

    private String getBranchType(JenkinsJob job) {
        String branchType = "";

        String name = urlRewriter.decodeStringInUtf8(job.getName());
        String url = job.getUrl();
        if (url.contains(SLASH + MASTER + SLASH)) {
            branchType = MASTER;
        } else if ((url.contains(SLASH + SNAPSHOT + SLASH))) {
            branchType = SNAPSHOT;
        } else if ((url.contains("--" + SCHEDULE))) {
            branchType = getMatchingPart(SCHEDULE_REGEX, url);
        } else if (name.matches(FEATURE_REGEX)) {
            branchType = FEATURE;
        } else if (name.matches(BUGFIX_REGEX)) {
            branchType = BUGFIX;
        }
        return branchType;
    }

    private String getMatchingPart(String regex, String url) {
        String headLine = "";
        if (regex != null) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(url);
            if (matcher.find()) {
                headLine = matcher.group(0);
            }
        }
        return headLine;
    }


}