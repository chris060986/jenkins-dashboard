package com.chbi.rest;

import com.chbi.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Class to create {@link org.springframework.http.HttpEntity} and Request. Also Responsible to add authentication details.
 */
@Service
public class RequestManager {

    @Autowired
    private ApplicationConfiguration configuration;


    public HttpEntity<String> getJsonHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        if (!StringUtils.isEmpty(configuration.getCredits())) {
            String base64Creds = "Basic " + configuration.getCredits();
            headers.add("Authorization", base64Creds);
        }

        return new HttpEntity<>(headers);
    }

}
