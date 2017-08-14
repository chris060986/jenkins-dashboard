package com.chbi.ui.entities;

/**
 * Class is representing a buildbox on the view.
 */
public class BuildBox {

    private String branchName;
    private int buildNumber;
    private String buildUrl;
    private String color;
    private String culprits;
    private String jiraTicket;

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

    public BuildBox withCulprits(String culprits) {
        this.culprits = culprits;
        return this;
    }

    public BuildBox withJiraTicket(String jiraTicket){
        this.jiraTicket = jiraTicket;
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

    public String getCulprits() {
        return culprits;
    }

    public String getJiraTicket() {
        return jiraTicket;
    }
}
