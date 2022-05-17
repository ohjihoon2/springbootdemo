package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Notification {

    public Notification(String text, Date time) {
        super();
        this.text = text;
        this.time = time;
    }

    public static Integer getNextJobId() {
        return ++jobId;
    }

    private String text;
    private Date time;
    private static Integer jobId = 0;
}
