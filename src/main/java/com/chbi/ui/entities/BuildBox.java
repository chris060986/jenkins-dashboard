package com.chbi.ui.entities;

/**
 * Class is representing a buildbox on the view.
 */
public class BuildBox {

    private String branchName;
    private int buildNumber;
    private String buildUrl;
    private String color;
    private String committer;

    public BuildBox withBranchname(String branchName) {
        this.branchName = branchName;
        return this;
    }

    public BuildBox withBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
        return this;
    }

    public BuildBox withBuildUrl(String lastBuildUrl) {
        this.buildUrl = lastBuildUrl;
        return this;
    }

    public BuildBox withColor(String color) {
        this.color = color;
        return this;
    }

    public BuildBox withCommiter(String committer) {
        this.committer = committer;
        return this;
    }

    public String getBranchName() {
        return branchName;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public String getColor() {
        return color;
    }

    public String getCommitter() {
        return committer;
    }
}
