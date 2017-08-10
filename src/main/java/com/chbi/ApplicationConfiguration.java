package com.chbi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfiguration {

    String baseUrl;

    public String getBaseUrl() {
        return baseUrl;
  }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
  }
}
