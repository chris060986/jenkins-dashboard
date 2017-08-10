package com.chbi.rest;

import com.chbi.json.entities.JenkinsBuild;
import com.chbi.json.entities.JenkinsBuildPipeline;
import com.chbi.json.entities.JenkinsJob;
import com.chbi.json.entities.JenkinsView;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataProvider {

    public static final String API_JSON_URL_ENDING = "api/json";

    public List<JenkinsJob> getJenkinsJobs(){
        RestTemplate restTemplate = new RestTemplate();


        JenkinsView jenkinsView = restTemplate.getForObject("https://jenkins.mono-project.com/view/All/api/json", JenkinsView.class);
        return jenkinsView.getJobs();
    }

    public JenkinsBuild getLastBuild(JenkinsJob jenkinsJob){
        RestTemplate restTemplate = new RestTemplate();

        String url = prepareUrl(jenkinsJob.getUrl());

        JenkinsBuildPipeline pipeline = restTemplate.getForObject(url, JenkinsBuildPipeline.class);

        JenkinsBuild jenkinsBuild = new JenkinsBuild();

        //lastbuild is null, if buildPipeline was never executed
        if (pipeline.getLastBuild() != null) {
            jenkinsBuild.setNumber(pipeline.getLastBuild().getNumber());
            jenkinsBuild.setUrl(pipeline.getLastBuild().getUrl());
            jenkinsBuild.set_class(pipeline.getLastBuild().get_class());
        } else {
            jenkinsBuild.setNumber(-1);
            jenkinsBuild.setUrl(jenkinsJob.getUrl());
            jenkinsBuild.set_class("never build");
        }
        return jenkinsBuild;
    }

    public String getChangingUserFor(String lastBuildUrl) {
        return "bich152";
    }

    private String prepareUrl(String jenkinsJobUrl) {

        return jenkinsJobUrl+= API_JSON_URL_ENDING;

    }
}
