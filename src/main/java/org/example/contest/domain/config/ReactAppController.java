package org.example.contest.domain.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactAppController {

    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String redirect() {
        // 모든 경로를 React의 index.html로 리다이렉트
        return "forward:/";
    }
    @GetMapping("/")
    public String home() {
        return "index";
    }
}