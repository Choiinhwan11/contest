package org.example.contest.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping(path = "/user")
public class UserController {

    // Logger 생성
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/getUserInfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpSession session) {
        logger.info("getUserInfo 요청 수신"); // 요청 로그

        // 세션에서 사용자 정보를 가져옴
        Map<String, Object> userInfo = (Map<String, Object>) session.getAttribute("userInfo");

        if (userInfo == null) {
            logger.warn("세션에 유저 정보가 없습니다."); // 세션에 정보가 없을 때 경고 로그
            Map<String, Object> response = new HashMap<>();
            response.put("message", "유저 정보 없음");
            return ResponseEntity.ok(response); // 404 대신 메시지를 JSON으로 반환
        }

        if (userInfo.isEmpty()) {
            logger.warn("세션에 저장된 유저 정보가 비어 있습니다."); // 정보가 비어 있을 때 경고 로그
            Map<String, Object> response = new HashMap<>();
            response.put("message", "유저 정보 없음");
            return ResponseEntity.ok(response);
        }

        // 유저 정보가 세션에 있을 경우 해당 정보 출력
        logger.info("유저 정보: {}", userInfo);

        // 사용자 정보를 반환 (JSON 형식으로 반환)
        return ResponseEntity.ok(userInfo);
    }
}
