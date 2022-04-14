package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Faq {
    private int idx;
    private String faqGroup;
    private String faqQuestion;
    private String faqAnswer;
    private String useYn;
    private String createIdx;
    private Date createDate;
    private String updateIdx;
    private Date updateDate;
}
