package com.chbi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfiguration {

    String baseUrl;

    @Value("${url.searchPattern}")
    String urlSearchPattern;
    @Value("${url.replacement}")
    String urlReplacement;

    public String getBaseUrl() {
        return baseUrl;
  }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
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
}
