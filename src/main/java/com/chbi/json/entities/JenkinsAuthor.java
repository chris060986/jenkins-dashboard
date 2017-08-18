package com.chbi.json.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.Preconditions;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JenkinsAuthor {

    private static final String ABSOLUTE_URL = "absoluteUrl";
    private static final String FULL_NAME = "fullName";
    private String absoluteUrl;
    private String fullName;

    public JenkinsAuthor() {
    }

    public JenkinsAuthor(Map rawAuthor) {
        Preconditions.checkState(rawAuthor.containsKey(ABSOLUTE_URL));
        Preconditions.checkState(rawAuthor.containsKey(FULL_NAME));
        absoluteUrl = rawAuthor.get(ABSOLUTE_URL).toString();
        fullName = rawAuthor.get(FULL_NAME).toString();
    }

    public String getAbsoluteUrl() {
        return absoluteUrl;
    }

    public void setAbsoluteUrl(String absoluteUrl) {
        this.absoluteUrl = absoluteUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JenkinsAuthor that = (JenkinsAuthor) o;

        if (absoluteUrl != null ? !absoluteUrl.equals(that.absoluteUrl) : that.absoluteUrl != null) return false;
        return fullName != null ? fullName.equals(that.fullName) : that.fullName == null;
    }

    @Override
    public int hashCode() {
        int result = absoluteUrl != null ? absoluteUrl.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }
}
