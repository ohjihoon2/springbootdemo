package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Alarm {

    public Alarm(String alertType, String alertContent, String url, int receiveIdx, Date createDate) {
        super();
        this.alertType = alertType;
        this.alertContent = alertContent;
        this.url = url;
        this.receiveIdx = receiveIdx;
        this.createDate = createDate;
    }

    public Alarm(String alertContent, Date createDate) {
        super();
        this.alertContent = alertContent;
        this.createDate = createDate;
    }

    private int idx;
    private String alertType;
    private String alertContent;
    private String url;
    private String readYn;
    private int receiveIdx;
    private Date createDate;
}
