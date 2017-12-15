package com.chbi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfiguration {

    String baseUrl;
    String credits;

    @Value("${url.searchPattern}")
    String urlSearchPattern;
    @Value("${url.replacement}")
    String urlReplacement;

    String jiraBaseUrl;
    String jiraTaskRegEx;

    Map<String, String> swimlanes;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getUrlSearchPattern() {
        return urlSearchPattern;
    }

    public void setUrlSearchPattern(String urlSearchPattern) {
        this.urlSearchPattern = urlSearchPattern;
    }

    public String getUrlReplacement() {
        return urlReplacement;
    }

    public void setUrlReplacement(String urlReplacement) {
        this.urlReplacement = urlReplacement;
    }

    public String getJiraTaskRegEx() {
        return jiraTaskRegEx;
    }

    public void setJiraTaskRegEx(String jiraTaskRegEx) {
        this.jiraTaskRegEx = jiraTaskRegEx;
    }

    public String getJiraBaseUrl() {
        return jiraBaseUrl;
    }

    public void setJiraBaseUrl(String jiraBaseUrl) {
        this.jiraBaseUrl = jiraBaseUrl;
    }

    public Map<String, String> getSwimlanes() {
        return swimlanes;
    }

    public void setSwimlanes(Map<String, String> swimlanes) {
        this.swimlanes = swimlanes;
    }
}
