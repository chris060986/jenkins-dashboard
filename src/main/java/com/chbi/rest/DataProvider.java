package com.chbi.rest;

import com.chbi.jenkins.JenkinsBuild;
import com.chbi.jenkins.JenkinsJob;
import com.chbi.jenkins.JenkinsView;
import com.google.common.collect.Lists;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sun.net.www.http.HttpClient;
import sun.net.www.protocol.http.AuthCache;

import java.net.URI;
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
