package org.example.contest.domain.user.service;


import java.util.Map;

public interface NaverLoginService {


    Map<String, Object> processNaverLogin(String code, String state) throws Exception;
}
