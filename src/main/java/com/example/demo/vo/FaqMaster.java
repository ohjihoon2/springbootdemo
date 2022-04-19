package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FaqMaster {
    private int idx;
    private String faqId;
    private String faqNm;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
