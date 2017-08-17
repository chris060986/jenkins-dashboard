package com.chbi.rest;

import com.chbi.ApplicationConfiguration;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Handles the jenkins urls, appends the json api suffix and replaces url parts if necessary.
 */
@Service
public class UrlRewriter {

    private static final String API_JSON_URL_ENDING = "api/json";

    @Autowired
    private ApplicationConfiguration configuration;

    String prepareUrl(String baseUrl) {
        String preparedUrl = StringUtils.replace(baseUrl, configuration.getUrlSearchPattern(),
                configuration.getUrlReplacement());
        try {
            preparedUrl = URLDecoder.decode(preparedUrl, "UTF-8");

            preparedUrl += API_JSON_URL_ENDING;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return preparedUrl;
    }

    String getPreparedBaseUrl() {
        return prepareUrl(configuration.getBaseUrl());
    }

    public String prepareJiraUrl(String ticketNumber){
        if(Strings.isNullOrEmpty(configuration.getBaseUrl())){
            return "";
        }
        return configuration.getJiraBaseUrl() + "browse/" + ticketNumber;
    }
}
