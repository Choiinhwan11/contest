package org.example.contest.domain.weather.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherConfig {

    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.serviceKey}")
    private String serviceKey;

    @Bean
    public String apiUrl() {
        return apiUrl;
    }

    @Bean
    public String serviceKey() {
        return serviceKey;
    }
}
