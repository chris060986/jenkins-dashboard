package com.chbi.json.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsBuildPipeline {

    JenkinsBuild lastBuild;

    public JenkinsBuildPipeline() {
    }

    public JenkinsBuild getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(JenkinsBuild lastBuild) {
        this.lastBuild = lastBuild;
    }
}
