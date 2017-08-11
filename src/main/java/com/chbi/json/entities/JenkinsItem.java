package com.chbi.json.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsItem {

    private JenkinsAuthor author;

    public JenkinsItem() {
    }

    public JenkinsAuthor getAuthor() {
        return author;
    }

    public void setAuthor(JenkinsAuthor author) {
        this.author = author;
    }
}
