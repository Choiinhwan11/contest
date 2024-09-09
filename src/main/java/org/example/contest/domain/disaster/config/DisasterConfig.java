package org.example.contest.domain.disaster.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration

public class DisasterConfig {

    @Value("${disaster.api.url}")
    private String disasterApiUrl;

    @Value("${disaster.api.serviceKey}")
    private String disasterServiceKey;

    @Bean(name = "disasterApiUrl")
    public String disasterApiUrl() {
        return disasterApiUrl;
    }

    @Bean(name = "disasterServiceKey")
    public String disasterServiceKey() {
        return disasterServiceKey;
    }
}
