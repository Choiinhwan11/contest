package org.example.contest.domain.payment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NaverPayServiceImpl implements NaverPayService {
    @Override
    public String createPaymentUrl(String productName, int amount) {
        // 엔드포인트 설정: 올바른 URL로 설정하세요. (테스트 환경)
        String naverPayUrl = "https://dev.apis.naver.com/naverpay/payments/v2.0/request";

        // 운영 환경용 URL (배포 시 사용)
        // String naverPayUrl = "https://apis.naver.com/naverpay/payments/v2.0/request";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", "HN3GGCMDdTgGUfl0kFCo"); //Client ID
        headers.set("X-Naver-Client-Secret", "ftZjkkRNMR"); //Client Secret
        headers.set("X-NaverPay-Chain-Id", "b2ZxeTJiNko1TUh"); //  Chain ID

        // 요청 본문 설정
        Map<String, Object> body = new HashMap<>();
        body.put("merchantId", "np_omagp520559"); // 가맹점 ID 사용
        body.put("productName", productName); // 상품명
        body.put("amount", amount); // 금액
        body.put("currency", "KRW"); //
        body.put("returnUrl", "http://localhost:3000/payment/success"); // 성공 URL
        body.put("failUrl", "http://localhost:3000/payment/fail"); // 실패 URL

        // 요청 보내기
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(naverPayUrl, HttpMethod.POST, entity, String.class);

            // 상태 코드 및 헤더 확인
            System.out.println("Naver Pay API response status code: " + response.getStatusCode());
            System.out.println("Naver Pay API response headers: " + response.getHeaders());

            // 응답 확인
            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                System.out.println("Received response from Naver Pay: " + responseBody);

                if (responseBody != null && !responseBody.isEmpty()) {
                    // JSON  파싱

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonResponse = objectMapper.readTree(responseBody);
                    String paymentUrl = jsonResponse.path("paymentUrl").asText();
                    return paymentUrl;
                } else {
                    System.out.println("Received empty response body from Naver Pay.");
                    return null;
                }
            } else {
                System.out.println("Naver Pay status : " + response.getStatusCode());
                System.out.println("Response body: " + response.getBody());
                return null;
            }
        } catch (Exception e) {
            System.out.println(" Naver : " + e.getMessage());
            return null;
        }
    }
}
