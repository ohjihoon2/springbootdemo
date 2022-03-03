package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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


        if(principal != null){
            System.out.println("principal.toString() = " + principal.toString());
        }
        mav.addObject("auth",auth);
        mav.setViewName("index");


        return mav;
    }

}
