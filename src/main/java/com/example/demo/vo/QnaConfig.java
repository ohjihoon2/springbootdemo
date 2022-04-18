package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QnaConfig {
    private int idx;
    private int qnaIdx;
    private String qaEmailRecvYn;
    private String qaCategory;
    private String qaStatusYn;
    private String secretYn;
    private int updateIdx;
    private Date updateDate;
}
