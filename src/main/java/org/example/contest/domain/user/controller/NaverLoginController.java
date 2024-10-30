package org.example.contest.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.contest.domain.user.DTO.UserDTO;
import org.example.contest.domain.user.service.NaverLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping(path = "/naver")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class NaverLoginController {

    private final NaverLoginService naverLoginService;
    private static final Logger logger = LoggerFactory.getLogger(NaverLoginController.class);

    public NaverLoginController(NaverLoginService naverLoginService) {
        this.naverLoginService = naverLoginService;
    }

    @GetMapping(value = "/login")
    public String naverLoginCallback(@RequestParam String code,
                                     @RequestParam String state,
                                     HttpServletRequest request) {
        try {
            // 세션 가져오기
            HttpSession session = request.getSession(true);

            // 세션 ID 로그 출력
            logger.debug("현재 세션 ID: {}", session.getId());

            // 네이버 로그인 정보 가져오기
            Map<String, Object> userInfo = naverLoginService.processNaverLogin(code, state);

            if (userInfo == null || userInfo.isEmpty()) {
                throw new Exception("Failed Naver Login.");
            }

            // DTO로 변환 후 세션에 저장
            UserDTO userDTO = UserDTO.builder()
                    .id((Long) userInfo.get("id"))
                    .userId((String) userInfo.get("userId"))
                    .userName((String) userInfo.get("userName"))
                    .userEmail((String) userInfo.get("userEmail"))
                    .userPhone((String) userInfo.get("userPhone"))
                    .userNickName((String) userInfo.get("userNickName"))
                    .build();

            logger.debug("유저 정보가 세션에 저장되기 전: userDTO = {}", userDTO);

            // 세션에 userDTO 저장
            session.setAttribute("userInfo", userDTO);

            // 세션에 유저 정보가 저장되었는지 확인하는 로그 추가
            UserDTO userInfoFromSession = (UserDTO) session.getAttribute("userInfo");
            if (userInfoFromSession != null) {
                logger.debug("세션에 유저 정보가 저장되었습니다: {}", userInfoFromSession.getUserName());
                logger.debug("저장된 세션 ID: {}", session.getId());
            } else {
                logger.error("세션에 유저 정보가 저장되지 않았습니다.");
            }

            // 로그인 성공 시 React 앱으로 리다이렉트
            return "redirect:http://localhost:3000/";
        } catch (Exception e) {
            logger.error("네이버 로그인 중 오류 발생", e);
            return "redirect:http://localhost:3000/login/loginFailure";
        }
    }
}
