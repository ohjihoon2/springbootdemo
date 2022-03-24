package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static java.lang.System.out;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @RequestMapping("/")
    public ModelAndView home(ModelAndView mav, HttpServletRequest request, Principal principal, Authentication auth) {


        log.debug("auth : {}",auth);

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = auth.getPrincipal();

//        String name = "";
//        if(principal != null){
//            name = ((MemberInfo)principal).getName();
//        }

        Cookie[] cookies = request.getCookies();

        // 2) 읽은 쿠기 정보 출력하기
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                out.printf("%s=%s\n",
                        cookie.getName(),  // 쿠키 이름
                        cookie.getValue());  // 쿠키 값
            }
        } else {
            out.println("쿠키가 한 개도 없습니다.");
        }

        mav.addObject("auth",auth);
        mav.setViewName("index");


        return mav;
    }

}
