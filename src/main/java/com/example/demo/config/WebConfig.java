package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Value("${resources.location}")
    private String resourcesLocation;
    @Value("${resources.uri_path}")
    private String resourcesUriPath;

    @Bean
    public CssInterceptor cssInterceptor(){
        return new CssInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // URI 가 resourcesUriPath 이하로 요청이 될 경우 로컬의 resourcesLocation 경로에 있는 파일을 사용자에게 제공
        registry.addResourceHandler(resourcesUriPath)
                .addResourceLocations("file:///" + resourcesLocation);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cssInterceptor())
                .order(1) // 해당 인터셉터가 적용되는 순서, 1 이면 첫번째로 실행
//                .addPathPatterns("/**"); // 현재 모든 주소에 대해 인터섹터 적용
                .addPathPatterns("/","/board/**","/content/**","/qna/**","/faq/**"); // 현재 모든 주소에 대해 인터섹터 적용
//                .excludePathPatterns("/board/hit/**", "/content/hit/**","/board/boardMaster/**"); // 그중에 이 주소는 제외
    }
}
