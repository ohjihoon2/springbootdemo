package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class CodeGroup {

    /* IDX */
    private int idx;

/* 코드그룹 ID */
    private String codeGroupId;

    /* 코드그룹 명 */
    private String codeGroupNm;

    /* 등록 IDX */
    private int createIdx;

    /* 등록 날짜 */
    private Date createDate;

    /* 수정 IDX */
    private int updateIdx;

    /* 수정 날짜 */
    private Date updateDate;

}
