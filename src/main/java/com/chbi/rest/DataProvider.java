package com.chbi.rest;

import com.chbi.json.entities.*;
import com.google.common.base.Joiner;
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

    private static final String UNKNOWN_USER = "unknown";
    @Autowired
    private RequestManager requestManager;
    @Autowired
    private UrlRewriter urlRewriter;


    public List<JenkinsJob> getJenkinsJobs() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = requestManager.getJsonHttpEntity();

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

        HttpEntity<String> request = requestManager.getJsonHttpEntity();

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

        HttpEntity<String> request = requestManager.getJsonHttpEntity();

        String url = urlRewriter.prepareUrl(lastBuildUrl);

        ResponseEntity<JenkinsBuildInstance> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsBuildInstance.class);
        JenkinsBuildInstance buildInstance = response.getBody();

        String users = UNKNOWN_USER;
        if (buildInstance.getCulprits() != null && buildInstance.getCulprits().size() > 0) {
            users = Joiner.on(", ").join(buildInstance.getCulprits());
        }

        return users;
    }

}
