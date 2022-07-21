package com.example.demo.config;

import com.example.demo.service.CommonService;
import com.example.demo.vo.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
@RequiredArgsConstructor
public class SessionListener implements HttpSessionListener {

    @Value("${server.servlet.session.timeout}")
    private int sessionTime;

    private final CommonService commonService;

    @Override
    public void sessionCreated(HttpSessionEvent event){
        event.getSession().setMaxInactiveInterval(sessionTime);
        HttpServletRequest req =  ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        Visitor vo = new Visitor();
        vo.setVisitIp(req.getRemoteAddr());
        vo.setVisitAgent(req.getHeader("User-Agent"));//브라우저 정보
        vo.setVisitRefer(req.getHeader("referer"));//접속 전 사이트 정보
        commonService.insertVisitor(vo);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent arg0){
        //TODO Auto-generated method stub
    }
}
