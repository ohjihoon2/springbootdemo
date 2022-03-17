package com.example.demo.config;

import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;
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

            String loginFailMsg = "";
            // 실패로그를 남긴다
             if(exception instanceof BadCredentialsException) {
                 loginFailMsg = "아이디 또는 비밀번호가 틀립니다.";

            } else if(exception instanceof LockedException) {
                 loginFailMsg = "잠긴 계정입니다.";

            } else if(exception instanceof DisabledException) {
                 loginFailMsg = "비활성화된 계정입니다.";

            } else if(exception instanceof AccountExpiredException) {
                 loginFailMsg = "만료된 계정입니다.";
            } else if(exception instanceof CredentialsExpiredException) {
                 loginFailMsg = "비밀번호가 만료되었습니다.";
            }

            JSONObject jsonObj = new JSONObject();

            jsonObj.put("result","fail");
            jsonObj.put("message",loginFailMsg);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(jsonObj);

        }
}
