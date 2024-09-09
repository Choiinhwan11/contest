package org.example.contest.domain.map.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@PropertySource("classpath:application.properties")

public class LocationConfig {
    @Bean(name = "NaverClientId")
    @Primary
    public String naverClientId(@Value("${naver.api.client-id}") String naverClientId) {
        System.out.println("config Id " + naverClientId);
        return naverClientId;
    }

    @Bean(name = "NaverClientSecret")
    public String naverClientSecret(@Value("${naver.api.client-secret}") String naverClientSecret) {
        System.out.println("config pwd " + naverClientSecret);
        return naverClientSecret;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();  // RestTemplate 빈 생성
    }

}
