package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Popup {
    private int idx;
    private String popSubject;
    private int attachFileIdx;
    private String startDate;
    private String endDate;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
