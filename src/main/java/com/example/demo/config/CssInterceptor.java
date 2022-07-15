package com.example.demo.config;

import com.example.demo.vo.Css;
import com.example.demo.vo.SingletonData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
public class CssInterceptor implements HandlerInterceptor {

//    @Autowired
//    AlarmService alarmService;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        String requestURI = request.getRequestURI();//프로젝트경로부터 파일까지의 경로값을 얻어옴 (/test/index.jsp)
        requestURI = requestURI.replaceFirst("/","");

        String method = request.getMethod();
        if(!method.equals("GET")){
            return;
        }
        String[] urlSplit = requestURI.split("/");

        List<Css> cssList = SingletonData.getInstance().getCssList();
        StringBuffer cssStyle = new StringBuffer();

        if(urlSplit[0].equals("")){
            for (Css css : cssList) {
                // main, common은 lvl2가 없다는 가정하에 진행
                if(css.getCssFirstId().equals("main") || css.getCssFirstId().equals("common")){
                    cssStyle.append("\n/* ------------------------------");
                    cssStyle.append(css.getCssNm());
                    cssStyle.append("------------------------------ */\n");
                    cssStyle.append(css.getCssCode()+"\n");
                }
            }
        }else {
            if(urlSplit[0].equals("board")) {
                for (Css css : cssList) {
                    if(css.getCssFirstId().equals("common") || (css.getCssFirstId().equals("board"))){
                        cssStyle.append("\n/* ------------------------------");
                        cssStyle.append(css.getCssNm());
                        cssStyle.append("------------------------------ */\n");
                        cssStyle.append(css.getCssCode()+"\n");
                    }
                }
            }else if(urlSplit[0].equals("content")){
                for (Css css : cssList) {
                    if(css.getCssFirstId().equals("common") || css.getCssFirstId().equals("content")){
                        cssStyle.append("\n/* ------------------------------");
                        cssStyle.append(css.getCssNm());
                        cssStyle.append("------------------------------ */\n");
                        cssStyle.append(css.getCssCode()+"\n");
                    }
                }
            }else {
                for (Css css : cssList) {
                    if(css.getCssFirstId().equals("common") || css.getCssFirstId().equals(urlSplit[0]))
                    cssStyle.append("\n/* ------------------------------");
                    cssStyle.append(css.getCssNm());
                    cssStyle.append("------------------------------ */\n");
                    cssStyle.append(css.getCssCode()+"\n");
                }
            }
        }

        modelAndView.addObject("cssStyle",cssStyle);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}

