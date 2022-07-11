package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class CssInterceptor implements HandlerInterceptor {

//    @Autowired
//    AlarmService alarmService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("AlertInterceptor.postHandle");

        String requestURI = request.getRequestURI();//프로젝트경로부터 파일까지의 경로값을 얻어옴 (/test/index.jsp)
        requestURI = requestURI.replaceFirst("/","");

        String[] split = requestURI.split("/");

        if(split[0].equals("")){
            System.out.println("main 이다");
        }else{
            System.out.println("main 아니다");
            if(split.length == 1){
                System.out.println("2번째 없다");
            }else {
                System.out.println("2번째 있다 "+ split[1]);
            }
        }




        HttpSession session = request.getSession();
        int userIdx = 0;
        Integer alertCnt = null;
//
//        if(session.getAttribute("idx") != null){
//            userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
////            alertCnt = alarmService.countReadYn(userIdx);
//            log.debug("alertCnt : {}", alertCnt);
//
//            modelAndView.addObject("alertCnt",alertCnt);
//        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}

