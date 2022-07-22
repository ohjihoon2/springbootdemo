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

        String userAgent = req.getHeader("User-Agent");
        String user = userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //=================OS=======================
        if (userAgent.toLowerCase().indexOf("windows") >= 0 )
        {
            os = "Windows";
        } else if(userAgent.toLowerCase().indexOf("mac") >= 0)
        {
            os = "Mac";
        } else if(userAgent.toLowerCase().indexOf("x11") >= 0)
        {
            os = "Unix";
        } else if(userAgent.toLowerCase().indexOf("android") >= 0)
        {
            os = "Android";
        } else if(userAgent.toLowerCase().indexOf("iphone") >= 0)
        {
            os = "IPhone";
        }else{
            os = "Etc";
        }
        //===============Browser===========================
        if (userAgent.indexOf("Trident/7.0") > -1) {
            browser = "IE";
        }else if (userAgent.indexOf("MSIE 10") > -1) {
            browser = "IE";
        }else if (userAgent.indexOf("MSIE 9") > -1) {
            browser = "IE";
        }else if (userAgent.indexOf("MSIE 8") > -1) {
            browser = "IE";
        }else if (user.contains("edg")){
            browser="Edge";
        } else if (user.contains("safari") && user.contains("version")){
            browser="Safari";
        } else if ( user.contains("opr") || user.contains("opera")){
            browser="Opera";
        }else if(user.contains("samsung")){
            browser="SAMSUNG";
        }else if (user.contains("chrome")){
            browser="Chrome";
        } else if (user.contains("firefox")){
            browser="Firefox";
        } else if(user.contains("rv")){
            browser="IE";
        }else if(user.contains("Googlebot")){
            browser="Googlebot";
        }else if(user.contains("Yeti")) {
            browser = "Yeti";
        }else {
            browser ="Etc";
        }

        Visitor vo = new Visitor();
        vo.setVisitIp(req.getRemoteAddr());
        vo.setVisitAgent(userAgent);//브라우저 정보
        vo.setVisitRefer(req.getHeader("referer"));//접속 전 사이트 정보
        vo.setVisitOs(os);
        vo.setVisitBrowser(browser);

        commonService.insertVisitor(vo);
    }
    @Override
    public void sessionDestroyed(HttpSessionEvent arg0){
        //TODO Auto-generated method stub
    }
}
