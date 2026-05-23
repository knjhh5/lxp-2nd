package com.gnjhh.lxp_2nd.global.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        String errorMessage;

        if (loginId == null || loginId.isBlank()) {
            errorMessage = "아이디는 필수 입력 항목입니다.";
        } else if (password == null || password.isBlank()) {
            errorMessage = "비밀번호를 필수 입력 항목입니다.";
        } else {
            errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
        }

        request.setAttribute("errorMessage", errorMessage);
        request.getRequestDispatcher("/auth/login").forward(request, response);
    }
}
