package org.example.contest.domain.payment.controller;

import lombok.RequiredArgsConstructor;
import org.example.contest.domain.payment.service.NaverPayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class NaverPayController {

    private final NaverPayService naverPayService;

    @PostMapping("/naverpay")
    public ResponseEntity<Map<String, String>> handleNaverPay(@RequestBody Map<String, Object> paymentData) {
        System.out.println("naver pay ");


        String productName = (String) paymentData.get("productName");
        int amount;

        try {
            amount = Integer.parseInt(paymentData.get("amount").toString());
            System.out.println("Extracted amount: " + amount);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing amount: " + paymentData.get("amount"));
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid amount format"));
        }

        try {
            String paymentUrl = naverPayService.createPaymentUrl(productName, amount);
            System.out.println("Received response from Naver Pay: " + paymentUrl);

            Map<String, String> response = new HashMap<>();
            response.put("paymentUrl", paymentUrl);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("Error processing payment: " + e.getMessage());
            return ResponseEntity.status(500).body(Map.of("error", "Failed to process payment"));
        }
    }
}