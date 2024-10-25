package org.example.contest.domain.payment.service;

public interface NaverPayService {

    String createPaymentUrl(String productName, int amount);
}
