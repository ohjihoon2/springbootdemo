package com.example.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Enumeration;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//    public CustomAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {

//    public CustomAuthenticationFilter(CustomAuthenticationManager authenticationManager) {
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        System.out.println("CustomAuthenticationFilter.CustomAuthenticationFilter");
//        this.authenticationManager = authenticationManager;
        super.setAuthenticationManager(authenticationManager);

    }
/*

    public CustomAuthenticationFilter(String defaultFilterProcessesUrl) {
        super.setFilterProcessesUrl(defaultFilterProcessesUrl);
//        super(defaultFilterProcessesUrl);
    }
*/


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("CustomAuthenticationFilter.attemptAuthentication");

        Enumeration eParam = request.getParameterNames();

        System.out.println( " ------------ loop ------------");
        while (eParam.hasMoreElements()) {
            String pName = (String)eParam.nextElement();
            String pValue = request.getParameter(pName);

            System.out.println(pName + " : " + pValue + "<br>");
        }
        System.out.println(" -------------------------------");
        System.out.println("request.getParameter(\"userId\") = " + request.getParameter("userId"));
        System.out.println("request.getParameter(userPwd) = " + request.getParameter("userPwd"));
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getParameter("userId"), request.getParameter("userPwd"));
        System.out.println("authRequest = " + authRequest);

        setDetails(request, authRequest);
//        return authenticationManager.authenticate(authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
