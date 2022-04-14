package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;

@Component
@Data
public class Criteria {
    private int pageNum;
    private int amount;
    private String searchType;
    private String searchKeyword;
    private Map<String, Object> paramMap;



    // 생성자로 무조건 실행된다.
    // 기본 페이지를 1페이지에 10개씩 페이징 처리
    public Criteria(){
        this(1,10,null,null);
    }

    //매개변수로 들어오는 값을 이용하여 페이징 처리
    public Criteria(int pageNum, int amount, String searchType, String searchKeyword) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.searchType = searchType;
        this.searchKeyword = searchKeyword;
    }

    public void setPageNum(int pageNum) {
        if(pageNum<=0) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }
    }

//    public void setAmount(int pageCount) {
//        int cnt = this.amount;
//        if(pageCount != cnt) {
//            this.amount = cnt;
//        } else {
//            this.amount = pageCount;
//        }
//    }

    public int getPageStart() {
        return (this.pageNum - 1) * amount;
    }

// UriComponentsBuilder를 이용하여 링크 생성
    // UriComponentsBuilder = 웹페이지에서 매번 파라미터를 유지하는 일이 번거롭고 힘들 경우 사용
    // 여러개의 파라미터들을 연결하여 url 형태로 만들어 주는 기능
    // 컨트롤러에서 리다이렉트 시 여러 파라미터들을 일일이 다 addAttribute를 사용 하기 불편함 해소

    public String getListLink(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
//                .queryParam("searchType", searchType)
//                .queryParam("searchKeyword", searchKeyword)
                .queryParam("pageNum", pageNum)
                .queryParam("amount",amount);
        return builder.toUriString();
    }

    //검색 조건이 (T,W,C)로 구성되어 검색 조건을 배열로 만듬 Mybatis 동적 태그 활용
//    검색 조건이 'T'면 : 제목이 키워드인 항목을 검색
//    검색 조건이 'C'면 : 내용이 키워드인 항목을 검색
//    검색 조건이 'W'면 : 작성자가 키워드인 항목을 검색
    public String[] getTypeArr(){
        String[] strArr = searchType == null ? new String[]{} : searchType.split("");
        return strArr;
    }
}
