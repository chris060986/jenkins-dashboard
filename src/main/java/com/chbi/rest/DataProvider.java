package com.chbi.rest;

import com.chbi.ApplicationConfiguration;
import com.chbi.json.entities.JenkinsBuild;
import com.chbi.json.entities.JenkinsBuildPipeline;
import com.chbi.json.entities.JenkinsJob;
import com.chbi.json.entities.JenkinsView;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataProvider {

    public static final String API_JSON_URL_ENDING = "api/json";

    @Autowired
    private ApplicationConfiguration configuration;


    public List<JenkinsJob> getJenkinsJobs() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = getStringHttpEntity();

        String url = prepareUrl(configuration.getBaseUrl());

        System.out.println(url);

        ResponseEntity<JenkinsView> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsView.class);
        JenkinsView jenkinsView = response.getBody();

        return jenkinsView.getJobs();
    }

    //TODO: refactore to http request builder class
    private HttpEntity<String> getStringHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        if (StringUtils.isEmpty(configuration.getCredits())) {
            String base64Creds = "Basic " + configuration.getCredits();
            headers.add("Authorization", base64Creds);
        }

        return new HttpEntity<>(headers);
    }

    public List<JenkinsBuildPipeline> getBuildsFor(JenkinsJob jenkinsJob) {
        return Lists.newArrayList();
    }

    public JenkinsBuild getLastBuild(JenkinsJob jenkinsJob) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = getStringHttpEntity();

        String url = prepareUrl(jenkinsJob.getUrl());

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

        HttpEntity<String> request = getStringHttpEntity();

        String url = prepareUrl(lastBuildUrl);

//        ResponseEntity<JenkinsBuildPipeline> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsBuildPipeline.class);
//        JenkinsBuildPipeline jenkinsBuildPipeline = response.getBody();

        return "bich152";
    }

    //TODO: refactore to url handling class
    private String prepareUrl(String jenkinsJobUrl) {

        String preparedUrl = StringUtils.replace(jenkinsJobUrl, configuration.getUrlSearchPattern(),
                configuration.getUrlReplacement());
        preparedUrl += API_JSON_URL_ENDING;

        return preparedUrl;
    }
}
