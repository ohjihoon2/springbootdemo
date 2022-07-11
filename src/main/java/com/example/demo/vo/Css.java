package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Css {
    private int idx;
    private String cssFirstId;
    private String cssSecondId;
    private String cssNm;
    private String cssCode;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
