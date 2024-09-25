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

        UserDTO userInfo = (UserDTO) session.getAttribute("userInfo");
        System.out.println(userInfo + "userInfo usercontroller ");
        Map<String, Object> response = new HashMap<>();

        if (userInfo == null) {
            System.out.println("세션에 유저 정보가 없습니다.");
            response.put("message", "유저 정보 없음");
        } else {
            response.put("userId", userInfo.getUserId());
            response.put("userName", userInfo.getUserName());
            response.put("userEmail", userInfo.getUserEmail());

            System.out.println("세션에서 가져온 유저 이름: " + userInfo.getUserName());
            System.out.println("세션에서 가져온 유저 이름: " + userInfo.getUserId());
            System.out.println("세션에서 가져온 유저 이메일: " + userInfo.getUserEmail());
        }

        return ResponseEntity.ok(response);
    }
}
