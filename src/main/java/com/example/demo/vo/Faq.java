package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Faq {
    private int idx;
    private String masterIdx;
    private String faqQuestion;
    private String faqAnswer;
    private String faqOrder;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
