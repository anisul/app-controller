package com.grasshopper.app.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NexusApiClient implements BaseClient {

    @Value("${application.api.nexusApi.uri}")
    String apiUri;

    @Override
    public String call() {
        // call URI
        // get response
        // return response
        return null;
    }
}
