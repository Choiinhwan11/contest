package org.example.contest.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.user.entity.ProviderType;
import org.example.contest.domain.user.entity.User;
import org.example.contest.domain.user.repository.NaverLoginRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NaverLoginServiceImpl implements NaverLoginService {

    private final NaverLoginRepository naverLoginRepository;
    @Qualifier("restTemplateForNaver")
    private final RestTemplate restTemplate;

    @Value("${naver.login.api.client-id}")
    private String clientId;

    @Value("${naver.login.api.client-secret}")
    private String clientSecret;

    @Value("${naver.login.api.redirect-uri}")
    private String redirectUri;

    @Override
    public Map<String, Object> processNaverLogin(String code, String state) throws Exception {
        try {
            URI tokenUri = UriComponentsBuilder.fromUriString("https://nid.naver.com/oauth2.0/token")
                    .queryParam("grant_type", "authorization_code")
                    .queryParam("client_id", clientId)
                    .queryParam("client_secret", clientSecret)
                    .queryParam("code", code)
                    .queryParam("state", state)
                    .build()
                    .toUri();

            ResponseEntity<Map> tokenResponse = restTemplate.exchange(tokenUri, HttpMethod.POST, null, Map.class);
            Map<String, Object> tokenResponseBody = tokenResponse.getBody();

            if (tokenResponseBody == null || !tokenResponseBody.containsKey("access_token")) {
                throw new Exception("Failed to retrieve access token from Naver.");
            }

            String accessToken = (String) tokenResponseBody.get("access_token");

            URI userInfoUri = UriComponentsBuilder.fromUriString("https://openapi.naver.com/v1/nid/me")
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> userInfoResponse = restTemplate.exchange(userInfoUri, HttpMethod.GET, entity, Map.class);

            if (userInfoResponse.getBody() == null || !userInfoResponse.getBody().containsKey("response")) {
                throw new Exception("Failed to retrieve user info from Naver.");
            }

            Map<String, Object> userInfo = (Map<String, Object>) userInfoResponse.getBody().get("response");

            String naverId = (String) userInfo.get("id");
            String name = (String) userInfo.get("name");
            String email = (String) userInfo.get("email");

            return registerOrLogin(naverId, name, email);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error processing Naver login: " + e.getMessage(), e);
        }
    }

    private Map<String, Object> registerOrLogin(String naverId, String name, String email) {
        Optional<User> existingUser = Optional.ofNullable(naverLoginRepository.findByUserId(naverId));

        if (existingUser.isEmpty()) {
            User newUser = User.builder()
                    .userId(naverId)
                    .name(name)
                    .email(email)
                    .providerType(ProviderType.NAVER)
                    .build();
            naverLoginRepository.save(newUser);

            return Map.of(
                    "status", "success",
                    "message", "User registered successfully",
                    "userId", naverId,
                    "userName", name,
                    "userEmail", email
            );
        } else {
            User user = existingUser.get();
            return Map.of(
                    "status", "success",
                    "message", "User logged in successfully",
                    "userId", user.getUserId(),
                    "userName", user.getName(),
                    "userEmail", user.getEmail()
            );
        }
    }
}
