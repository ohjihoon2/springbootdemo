package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Qna {
    private int idx;
    private int parentIdx;
    private String qaEmailRecvYn;
    private String qaCategory;
    private String qaSubject;
    private String qaContent;
    private String qaStatusYn;
    private String qaFile;
    private String secretYn;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
