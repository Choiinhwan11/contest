package org.example.contest.domain.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class NaverLoginConfig {

    @Bean(name = "restTemplateForNaver")
    public RestTemplate restTemplateForNaver() {
        return new RestTemplate();
    }
}
