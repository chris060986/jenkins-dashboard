package com.chbi.jenkins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsView {

    String description;
    List<JenkinsJob> jobs;
    String name;
    String url;

    public JenkinsView() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<JenkinsJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<JenkinsJob> jobs) {
        this.jobs = jobs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "JenkinsView{" +
                "description='" + description + '\'' +
                ", jobs=" + jobs +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
