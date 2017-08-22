package com.chbi.ui.entities;

import com.chbi.ui.Dictionary;
import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class is representing a buildbox on the view.
 */
public class BuildBox {

    private String displayName;
    private String branchName;
    private int buildNumber;
    private String buildUrl;
    private JobColor color;
    private HashSet<String> culprits = new HashSet<>();
    private String jiraTicket;
    private String jiraUrl;
    //TODO: could be enum?!
    private String branchType;

    public BuildBox withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public BuildBox withBranchName(String branchName) {
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

    public BuildBox withColor(JobColor color) {
        this.color = color;
        return this;
    }

    public BuildBox withCulprits(String... culprits) {
        for (String culprit : culprits) {
            this.culprits.addAll(Arrays.asList(culprit.split(", ")));
        }
        return this;
    }

    public BuildBox withJiraTicket(String jiraTicket){
        this.jiraTicket = jiraTicket;
        return this;
    }

    public BuildBox withJiraUrl(String url){
        this.jiraUrl = url;
        return this;
    }

    public BuildBox withBranchType(String branchType){
        this.branchType = branchType;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDisplayNameShort() {
        return displayName.replaceFirst("[a-z]+\\/[A-Z]+-[0-9]+[-]+", "");
    }

    public String getBranchName() {
        return branchName;
    }

    public String getBranchNameShort() {
        return branchName.replaceFirst("^[a-z]+\\/[A-Z]+-[0-9]+[-]+", "");
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public String getBuildUrl() {
        return buildUrl;
    }

    public JobColor getColor() {
        return color;
    }

    public Set<String> getCulprits() {
        return culprits;
    }

    public String getCulprit() {
        if (this.culprits.isEmpty()) {
            return "unknown";
        } else {
            String culprits = Dictionary.lookupCulprit(this.culprits.iterator().next());

            if (this.culprits.size() > 1) {
                culprits = culprits.concat(" +" + (this.culprits.size() - 1));
            }

            return culprits;
        }
    }

    public String getJiraTicket() {
        return jiraTicket;
    }

    public String getJiraUrl() {
        return jiraUrl;
    }

    public String getBranchType() {
        return branchType;
    }

    public boolean hasJiraTicket(){
        return !Strings.isNullOrEmpty(jiraTicket);
    }

    public boolean hasBranchType(){
        return !Strings.isNullOrEmpty(branchType);
    }

    public boolean hasDisplayName() {
        return (displayName != null? !displayName.equals(branchName): false);
    }
}
