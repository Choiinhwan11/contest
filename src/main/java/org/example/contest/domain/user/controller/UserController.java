package org.example.contest.domain.user.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.contest.domain.user.DTO.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/user")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class UserController {

    @GetMapping("/getUserInfo")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpSession session) {
        // 현재 세션의 세션 ID 출력
        System.out.println("UserController에서 세션 ID: " + session.getId());

        // 세션에서 "userInfo"라는 키로 저장된 유저 정보 가져오기
        UserDTO userInfo = (UserDTO) session.getAttribute("userInfo");
        Map<String, Object> response = new HashMap<>();

        // 유저 정보가 세션에 없는 경우
        if (userInfo == null) {
            System.out.println("세션에 유저 정보가 없습니다.");
            response.put("message", "유저 정보 없음");
        } else {
            // 유저 정보가 있는 경우
            response.put("userInfo", userInfo);
            response.put("id", userInfo.getId());
            response.put("userId", userInfo.getUserId());
            response.put("userName", userInfo.getUserName());
            response.put("userEmail", userInfo.getUserEmail());
        }

        return ResponseEntity.ok(response);
    }

}
