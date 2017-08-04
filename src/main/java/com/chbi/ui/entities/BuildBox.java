package com.chbi.ui.entities;

import com.chbi.json.entities.JenkinsJob;

/**
 * Class is representing a buildbox on the view.
 * //TODO: implement filling and using this class instead of {@link JenkinsJob}.
 */
public class BuildBox {

    private String branchName;
    private int buildNumber;
    private String lastBuildUrl;
    private String color;
}
