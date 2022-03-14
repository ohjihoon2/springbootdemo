package com.example.demo.config;

import com.example.demo.service.LoginService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CustomAuthenicationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private LoginService loginService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("CustomAccessSuccessHandler.onAuthenticationSuccess");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        System.out.println("authentication.getPrincipal() = " + authentication.getPrincipal());
        String userId =  authentication.getPrincipal().toString();
        String userNicknm = loginService.findUserNicknmByUserId(userId);

        session.setAttribute("userNicknm", userNicknm);
        response.sendRedirect("/");
    }

}
