package com.example.demo.config;

import lombok.NoArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
            System.out.println("CustomAuthenticationFailureHandler.onAuthenticationFailure");

            System.out.println("exception.toString() = " + exception.toString());
            // 실패로그를 남긴다
             if(exception instanceof BadCredentialsException) {
                request.setAttribute("loginFailMsg", "아이디 또는 비밀번호가 틀립니다.");

            } else if(exception instanceof LockedException) {
                request.setAttribute("loginFailMsg", "잠긴 계정입니다.");

            } else if(exception instanceof DisabledException) {
                request.setAttribute("loginFailMsg", "비활성화된 계정입니다.");

            } else if(exception instanceof AccountExpiredException) {
                request.setAttribute("loginFailMsg", "만료된 계정입니다.");

            } else if(exception instanceof CredentialsExpiredException) {
                request.setAttribute("loginFailMsg", "비밀번호가 만료되었습니다.");
            }

            // 실패이벤트를 발송한다 - forword post 형식
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login-error");
            dispatcher.forward(request,response);
        }
}
