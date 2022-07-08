package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static java.lang.System.out;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Principal principal, Authentication auth, Model model) {

        log.debug("auth : {}",auth);
        log.debug("principal : {}",principal);
/*
        int alertCnt = 0;

        if(principal != null){
            HttpSession session = request.getSession();
            int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
            alertCnt = alertService.countReadYn(userIdx);
        }

        log.debug("alertCnt : {}", alertCnt);
*/
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

        model.addAttribute("auth",auth);
//        model.addAttribute("alertCnt",alertCnt);

        return "index";
    }
}
