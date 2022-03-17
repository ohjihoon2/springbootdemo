package com.example.demo.config;

import com.example.demo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private LoginService loginService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = loginService.loadUserByUsername(authentication.getPrincipal().toString());
        // 각종 처리를 구현
        // 비번이 일치하는지
        // 아이디로 회원을 조회 했을 때 존재하는 회원인지
        // 기타 등등과 적절한 예외 처리
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername()
                , userDetails.getPassword()
                , userDetails.getAuthorities());
    }
}
