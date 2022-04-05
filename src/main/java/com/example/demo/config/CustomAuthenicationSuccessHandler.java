package com.example.demo.config;

import com.example.demo.service.LoginService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class CustomAuthenicationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();

        String userId =  authentication.getPrincipal().toString();
        Map<String, Object> sessionMap = loginService.findUserNicknmVerificationYnByUserId(userId);
        System.out.println("sessionMap = " + sessionMap);
        session.setAttribute("userNicknm", sessionMap.get("userNicknm"));
        session.setAttribute("verificationYn", sessionMap.get("verificationYn"));
        JSONObject jsonObj = new JSONObject();

        jsonObj.put("result","success");
        jsonObj.put("message",null);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonObj);
    }

}
