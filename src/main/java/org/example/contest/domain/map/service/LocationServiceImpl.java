package org.example.contest.domain.map.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final  RestTemplate restTemplate;
    private final String naverClientId;  // Naver Client ID
    private final String naverClientSecret;  // Naver Client Secret

    // 생성자를 통해 주입받기
    public LocationServiceImpl(RestTemplate restTemplate,
                               @Qualifier("NaverClientId") String naverClientId,
                               @Qualifier("NaverClientSecret") String naverClientSecret) {
        this.restTemplate = restTemplate;
        this.naverClientId = naverClientId;
        this.naverClientSecret = naverClientSecret;
    }

    @Override
    public String getCityFromLocation(String latitude, String longitude) {
        try {
            // 디버깅을 위한 값 출력
            System.out.println("naverClientId: " + naverClientId);
            System.out.println("naverClientSecret: " + naverClientSecret);
            System.out.println("Latitude: " + latitude);
            System.out.println("Longitude: " + longitude);

            // 네이버 API 요청 URL
            String naverApiUrl = "https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-NCP-APIGW-API-KEY-ID", naverClientId);
            headers.set("X-NCP-APIGW-API-KEY", naverClientSecret);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            System.out.println("naver url " + naverApiUrl);


            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(naverApiUrl)
                    .queryParam("coords", longitude + "," + latitude)
                    .queryParam("output", "json")
                    .queryParam("orders", "legalcode,admcode");

            // HttpEntity에 헤더 추가
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // 네이버 API로 요청 보내기
            ResponseEntity<String> response = restTemplate.exchange(
                    uriBuilder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    String.class
            );

            // 네이버 API 응답 디버깅
            System.out.println("Response from Naver API: " + response.getBody());

            // 네이버 API 응답 반환
            return response.getBody();

        } catch (Exception e) {
            System.out.println("Error during API request: " + e.getMessage());
            throw new RuntimeException("네이버 API 요청에 실패했습니다: " + e.getMessage(), e);
        }
    }
}
