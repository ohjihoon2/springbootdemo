package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Alarm {

    public Alarm(String alarmType, String alarmContent, String url, int receiveIdx, Date createDate) {
        super();
        this.alarmType = alarmType;
        this.alarmContent = alarmContent;
        this.url = url;
        this.receiveIdx = receiveIdx;
        this.createDate = createDate;
    }

    public Alarm(String alarmContent, Date createDate) {
        super();
        this.alarmContent = alarmContent;
        this.createDate = createDate;
    }

    private int idx;
    private String alarmType;
    private String alarmContent;
    private String url;
    private String readYn;
    private int receiveIdx;
    private Date createDate;
}
