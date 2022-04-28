package com.example.demo.util;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class CompareAuth {

    public static boolean getAuth(String auth){
        // 인증 객체를 얻습니다.
        String authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        boolean response = false;
        String[] admin = {"[ROLE_SYSTEM]","[ROLE_ADMIN]","[ROLE_MANAGER]"};
        String[] user = {"[ROLE_SYSTEM]","[ROLE_ADMIN]","[ROLE_MANAGER]","[ROLE_USER]"};

        if(auth.equals("ALL")) {
            response = true;
        }
        else if(auth.equals("USER")) {
            for( String str : user){
                if(authorities.equals(str)) {
                    response = true;
                }
            }
        }
        else if(auth.equals("ADMIN")) {
            for( String str : admin){
                if(authorities.equals(str)) {
                    response = true;
                }
            }
        }

        return response;
    }
}
