package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class FaqMaster {
    private int idx;
    private String faqNm;
    private int fmOrder;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
