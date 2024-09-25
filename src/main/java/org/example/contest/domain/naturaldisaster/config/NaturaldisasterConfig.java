package org.example.contest.domain.naturaldisaster.config;

import org.hibernate.annotations.View;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration

public class NaturaldisasterConfig {

    // VWorld API 설정
    @Value("${vwold.api.url}")
    private String vWorldApiUrl;

    @Value("${vwold.api.key}")
    private String vWorldApiKey;
    @Value("${vworld.api.domain}")
    private String vWorldApiDomain;

    public String getVWorldApiDomain() {
        return vWorldApiDomain;
    }
    public String getVWorldApiUrl() {
        return vWorldApiUrl;
    }

    public String getVWorldApiKey() {
        return vWorldApiKey;
    }

}
