package org.example.contest.domain.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherConfig {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Value("${weather.api.serviceKey}")
    private String weatherServiceKey;

    @Bean(name = "weatherApiUrl")
    public String weatherApiUrl() {
        return weatherApiUrl;
    }

    @Bean(name = "weatherServiceKey")
    public String weatherServiceKey() {
        return weatherServiceKey;
    }
}
