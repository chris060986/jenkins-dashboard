package com.chbi.ui;

import com.chbi.json.entities.JenkinsBuild;
import com.chbi.json.entities.JenkinsJob;
import com.chbi.rest.DataProvider;
import com.chbi.ui.entities.BuildBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BuildJobController {

    private DataProvider dataProvider;

    @Autowired
    BuildJobController(DataProvider productService) {
        this.dataProvider = productService;
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
                .withCommiter(changingUsers).withColor(job.getColor());
    }


}