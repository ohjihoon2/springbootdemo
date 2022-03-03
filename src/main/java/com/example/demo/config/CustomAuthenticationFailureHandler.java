package com.example.demo.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
        private String defaultFailureUrl = "/login-error";

        public CustomAuthenticationFailureHandler(String defaultFailureUrl) {
            this.defaultFailureUrl = defaultFailureUrl;
        }

        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException ex) throws IOException, ServletException {
            // 실패로그를 남긴다
            // 실패이벤트를 발송한다

            response.sendRedirect(defaultFailureUrl);
        }
}
