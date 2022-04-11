package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Content {
    private int idx;
    private String contentId;
    private String contentNm;
    private String contentHtml;
    private int hit;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
