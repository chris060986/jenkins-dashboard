package com.chbi.json.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsBuild {

    JenkinsBuildInstance lastBuild;

    public JenkinsBuild() {
    }

    public JenkinsBuildInstance getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(JenkinsBuildInstance lastBuild) {
        this.lastBuild = lastBuild;
    }
}
