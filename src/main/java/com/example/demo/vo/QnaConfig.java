package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class QnaConfig {
    private int idx;
    private int qaIdx;
    private String qaEmailRecvYn;
    private String qaCategory;
    private String qaStatus;
    private String secretYn;
    private String deleteYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
