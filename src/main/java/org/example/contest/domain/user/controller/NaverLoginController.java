package org.example.contest.domain.user.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.contest.domain.user.service.NaverLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping(path = "/naver")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class NaverLoginController {

    private final NaverLoginService naverLoginService;

    public NaverLoginController(NaverLoginService naverLoginService) {
        this.naverLoginService = naverLoginService;
    }

    @GetMapping("/login")
    public String naverLoginCallback(@RequestParam String code,
                                     @RequestParam String state,
                                     RedirectAttributes redirectAttributes,
                                     HttpServletRequest request) {
        try {
            // 세션이 존재하는지 확인, 없으면 새로 생성
            HttpSession session = request.getSession(false);
            if (session == null) {
                session = request.getSession(true); // 세션이 없으면 새로 생성
            }
            Map<String, Object> userInfo = naverLoginService.processNaverLogin(code, state);

            if (userInfo == null || userInfo.isEmpty()) {
                throw new Exception("Failed Naver Login.");
            }

            session.setAttribute("userInfo", userInfo);
            redirectAttributes.addFlashAttribute("userInfo", userInfo);

            // React의 loginSuccess 페이지로 리다이렉트
            return "redirect:http://localhost:3000/login/loginSuccess";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:http://localhost:3000/login/loginFailure";
        }
    }
}
