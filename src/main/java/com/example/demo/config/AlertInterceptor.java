package com.example.demo.config;

import com.example.demo.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AlertInterceptor implements HandlerInterceptor {

    @Autowired
    AlertService alertService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("AlertInterceptor.preHandle");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("AlertInterceptor.postHandle");
        HttpSession session = request.getSession();
        int userIdx = 0;
        Integer alertCnt = null;

        if(session.getAttribute("idx") != null){
            userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
            alertCnt = alertService.countReadYn(userIdx);
            log.debug("alertCnt : {}", alertCnt);

            modelAndView.addObject("alertCnt",alertCnt);
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("AlertInterceptor.afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
