package com.chbi.json.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsBuildInstance {

    private String _class;
    private List<JenkinsAuthor> culprits;

    public JenkinsBuildInstance() {
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public List<JenkinsAuthor> getCulprits() {
        return culprits;
    }

    public void setCulprits(List<JenkinsAuthor> culprits) {
        this.culprits = culprits;
    }
}
