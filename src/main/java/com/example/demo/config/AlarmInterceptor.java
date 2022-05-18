package com.example.demo.config;

import com.example.demo.service.AlarmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class AlarmInterceptor implements HandlerInterceptor {

    @Autowired
    AlarmService alarmService;

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

        //TODO  05/17 - ALTER 테이블에 추가해서 테스트 하고 INTERCEPTOR CONTROLLER 추가해보기

        if(session.getAttribute("idx") != null){
            userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
            alertCnt = alarmService.countReadYn(userIdx);
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
