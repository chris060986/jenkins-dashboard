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

    private static final String API_JSON_URL_ENDING = "api/json";
    private static final String HTML_WHITESPACE = "%20";
    private static final String WHITESPACE = " ";

    @Autowired
    private ApplicationConfiguration configuration;

    String prepareUrl(String baseUrl) {

        String preparedUrl = StringUtils.replace(baseUrl, configuration.getUrlSearchPattern(),
                configuration.getUrlReplacement());
        preparedUrl = StringUtils.replace(preparedUrl, HTML_WHITESPACE, WHITESPACE);
        preparedUrl += API_JSON_URL_ENDING;

        return preparedUrl;
    }

    String getPreparedBaseUrl() {
        return prepareUrl(configuration.getBaseUrl());
    }
}
