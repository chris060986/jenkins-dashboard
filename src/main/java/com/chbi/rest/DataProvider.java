package com.chbi.rest;

import com.chbi.json.entities.JenkinsBuild;
import com.chbi.json.entities.JenkinsJob;
import com.chbi.json.entities.JenkinsView;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DataProvider {

    public List<JenkinsJob> getJenkinsJobs(){
        RestTemplate restTemplate = new RestTemplate();

        JenkinsView jenkinsView = restTemplate.getForObject("https://jenkins.mono-project.com/view/All/api/json", JenkinsView.class);
        return jenkinsView.getJobs();
    }

    public List<JenkinsBuild> getBuildsFor(JenkinsJob jenkinsJenkinsJob){
        return Lists.newArrayList();
    }

}
