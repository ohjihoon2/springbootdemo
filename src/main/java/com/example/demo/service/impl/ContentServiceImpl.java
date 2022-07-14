package com.example.demo.service.impl;

import com.example.demo.repository.ContentMapper;
import com.example.demo.service.ContentService;
import com.example.demo.util.HitCookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final ContentMapper contentMapper;


    @Override
    public Map<String, Object> findAllByContentId(String contentId) {
        return contentMapper.findAllByContentId(contentId);
    }

    @Override
    public void contentAddHit(HttpServletRequest request, HttpServletResponse response, int idx) {
        String formatIdx = String.format("%09d", idx);

        Cookie accumulateIdxCookie = Arrays
                .stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("contentCookie"))
                .findFirst()
                .orElseGet(() -> {
                    Cookie cookie = HitCookie.createAccIdxCookie("contentCookie", formatIdx);    // 조회수 중복 방지용 쿠키 생성
                    response.addCookie(cookie);
                    contentMapper.incrementContentHit(idx);                        // 조회수 증가 쿼리 수행
                    return cookie;
                });

        // 서로 다른 idx에 대해서는 "/" 로 구분한다.
        String cookieValue = accumulateIdxCookie.getValue();

        if(cookieValue.contains(formatIdx) == false) {
            String newCookieValue = cookieValue + "/" + formatIdx;
            response.addCookie(HitCookie.createAccIdxCookie("contentCookie", newCookieValue));    // 기존에 같은 이름의 쿠키가 있다면 덮어쓴다.
            contentMapper.incrementContentHit(idx);                        // 조회수 증가 쿼리 수행
        }
    }
}
