package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Code {
    /* IDX */
    private int idx;

    /* 코드그룹 ID */
    private String codeGroupId;

    /* 코드 */
    private String code;

    /* 코드명 */
    private String codeNm;

    /* 정렬 순서 */
    private int sortOrdr;

    /* 등록 IDX */
    private int createIdx;

    /* 등록 날짜 */
    private Date createDate;

    /* 수정 IDX */
    private int updateIdx;

    /* 수정 날짜 */
    private Date updateDate;


}
