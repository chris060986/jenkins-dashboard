package com.chbi.rest;

import com.chbi.json.entities.JenkinsJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobsResource {

    private final DataProvider dataProvider;

    @Autowired
    public JobsResource(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @RequestMapping(
            path = "/api/jobs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<JenkinsJob> allJobs() {
        return dataProvider.getJenkinsJobs();
    }
}
