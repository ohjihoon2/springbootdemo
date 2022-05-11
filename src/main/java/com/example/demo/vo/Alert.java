package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Alert {

    private int idx;
    private String alertType;
    private String alertContent;
    private String url;
    private String readYn;
    private int receiveIdx;
    private Date createDate;
}
