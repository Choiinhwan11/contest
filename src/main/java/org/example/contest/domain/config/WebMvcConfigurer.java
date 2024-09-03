package org.example.contest.domain.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

public interface WebMvcConfigurer {
    void addCorsMappings(CorsRegistry registry);


}
