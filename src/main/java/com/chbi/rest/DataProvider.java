package com.chbi.rest;

import com.chbi.json.entities.JenkinsBuild;
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

        JenkinsBuild jenkinsBuild = new JenkinsBuild();
        jenkinsBuild.setNumber(815);
        return jenkinsBuild;
    }

    public String getChangingUserFor(String lastBuildUrl) {

        return "bich152";
    }

    private String prepareUrl(String jenkinsJobUrl) {

        return jenkinsJobUrl+= API_JSON_URL_ENDING;

    }
}
