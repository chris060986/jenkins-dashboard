package com.chbi.json.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsBuildPipeline {

    JenkinsBuild lastBuild;
    String displayName;

    public JenkinsBuildPipeline() {
    }

    public JenkinsBuild getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(JenkinsBuild lastBuild) {
        this.lastBuild = lastBuild;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
