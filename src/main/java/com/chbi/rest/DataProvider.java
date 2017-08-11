package com.chbi.rest;

import com.chbi.json.entities.JenkinsBuild;
import com.chbi.json.entities.JenkinsBuildPipeline;
import com.chbi.json.entities.JenkinsJob;
import com.chbi.json.entities.JenkinsView;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataProvider {



    @Autowired
    private RequestManager requestManager;
    @Autowired
    private UrlRewriter urlRewriter;


    public List<JenkinsJob> getJenkinsJobs() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = requestManager.getStringHttpEntity();

        String url = urlRewriter.getPreparedBaseUrl();

        ResponseEntity<JenkinsView> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsView.class);
        JenkinsView jenkinsView = response.getBody();

        return jenkinsView.getJobs();
    }

    public List<JenkinsBuildPipeline> getBuildsFor(JenkinsJob jenkinsJob) {
        return Lists.newArrayList();
    }

    public JenkinsBuild getLastBuild(JenkinsJob jenkinsJob) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = requestManager.getStringHttpEntity();

        String url = urlRewriter.prepareUrl(jenkinsJob.getUrl());

        ResponseEntity<JenkinsBuildPipeline> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsBuildPipeline.class);
        JenkinsBuildPipeline pipeline = response.getBody();

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
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = requestManager.getStringHttpEntity();

        String url = urlRewriter.prepareUrl(lastBuildUrl);

//        ResponseEntity<JenkinsBuildPipeline> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsBuildPipeline.class);
//        JenkinsBuildPipeline jenkinsBuildPipeline = response.getBody();

        return "bich152";
    }

}
