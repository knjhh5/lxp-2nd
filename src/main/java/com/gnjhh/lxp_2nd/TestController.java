package com.gnjhh.lxp_2nd;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/auth/login")
    public String testLogin(HttpSession session) {
        session.setAttribute("loginMemberId", 3L);
        return "세션 설정 완료 - memberId: " + 3L;
    }
}