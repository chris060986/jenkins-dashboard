package com.chbi.rest;

import com.chbi.json.entities.*;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * TODO: if there is an error like 404 because jenkins is unavailable,
 * exceptions should be catched...
 */
@Service
public class DataProvider {

    private static final String UNKNOWN_USER = "Luke Buildwalker";
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

    public JenkinsBuildPipeline getJenkinsBuildPipeline(JenkinsJob jenkinsJob) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = requestManager.getJsonHttpEntity();

        String url = urlRewriter.prepareUrl(jenkinsJob.getUrl());

        ResponseEntity<JenkinsBuildPipeline> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsBuildPipeline.class);
        JenkinsBuildPipeline pipeline = response.getBody();

        //lastbuild is null, if buildPipeline was never executed
        if (pipeline.getLastBuild() == null) {
            pipeline.getLastBuild().setNumber(-1);
            pipeline.getLastBuild().setUrl(jenkinsJob.getUrl());
            pipeline.getLastBuild().set_class("never built");
        }
        return pipeline;
    }

    public JenkinsBuildInstance getBuildInstance(String lastBuildUrl) {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = requestManager.getJsonHttpEntity();

        String url = urlRewriter.prepareUrl(lastBuildUrl);

        ResponseEntity<JenkinsBuildInstance> response = restTemplate.exchange(url, HttpMethod.GET, request, JenkinsBuildInstance.class);
        return response.getBody();
    }

    public String getChangingUserFor(JenkinsBuildInstance buildInstance) {
        String users;
        if (buildInstance.getCulprits() != null && buildInstance.getCulprits().size() > 0) {
            users = Joiner.on(", ").join(buildInstance.getCulprits());
        } else if (buildInstance.getChangeSets() != null) {
            users = Joiner.on(", ").join(getAuthorsFromChangeSet(buildInstance.getChangeSets()));
        } else {
            users = UNKNOWN_USER;
        }

        return users;
    }

    private List<JenkinsAuthor> getAuthorsFromChangeSet(List<JenkinsChangeSets> changeSets) {
        Set<JenkinsAuthor> authors = Sets.newHashSet();

        changeSets.forEach(jenkinsChangeSets -> jenkinsChangeSets.getItems()
                .stream().filter(stringObjectMap -> stringObjectMap.containsKey("author"))
                .forEach(filteredObject -> authors.add(new JenkinsAuthor((Map) filteredObject.get("author")))));

        return Lists.newArrayList(authors.iterator());
    }

    public static String getUnknownUser() {
        return UNKNOWN_USER;
    }
}
