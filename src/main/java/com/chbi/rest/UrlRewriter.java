package com.chbi.rest;

import com.chbi.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Handles the jenkins urls, appends the json api suffix and replaces url parts if necessary.
 */
@Service
public class UrlRewriter {

    public static final String API_JSON_URL_ENDING = "api/json";

    @Autowired
    private ApplicationConfiguration configuration;

    String prepareUrl(String baseUrl) {

        String preparedUrl = StringUtils.replace(baseUrl, configuration.getUrlSearchPattern(),
                configuration.getUrlReplacement());
        preparedUrl += API_JSON_URL_ENDING;

        return preparedUrl;
    }

    String getPreparedBaseUrl() {
        return prepareUrl(configuration.getBaseUrl());
    }
}
