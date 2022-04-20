package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Faq {
    private int idx;
    private String masterIdx;
    private String faqQuestion;
    private String faqAnswer;
    private int fOrder;
    private String useYn;
    private String createIdx;
    private Date createDate;
    private String updateIdx;
    private Date updateDate;
}
