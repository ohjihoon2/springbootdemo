package com.example.demo.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Component
public class HitCookie {

    /**
     * 조회수 중복 증가(= 새로고침에 의한 조회수 증가)를 방지하기 위한 쿠키를 생성하는 메소드 <br>
     * 일반 게시판(공지사항, 자료실, 질의응답, FAQ, 사용자요청) 전용
     * @param cookieValue
     * @return
     */
    public static Cookie createAccIdxCookie(String cookieName, String cookieValue) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setComment("조회수 중복 증가 방지 쿠키");    // 쿠키 용도 설명 기재
        cookie.setMaxAge(getRemainSecondForTommorow());             // 하루를 준다.
        cookie.setHttpOnly(true);                // 클라이언트 단에서 javascript로 조작 불가
        return cookie;
    }

    /**
     * 다음 날 정각까지 남은 시간(초)
     */
    public static int getRemainSecondForTommorow() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tommorow = LocalDateTime.now().plusDays(1L).truncatedTo(ChronoUnit.DAYS);
        return (int) now.until(tommorow, ChronoUnit.SECONDS);
    }

}
