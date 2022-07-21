package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Visitor {
    String visitId;
    String visitIp;
    Date visitTime;
    String visitRefer;
    String visitAgent;
    String visitOs;
    String visitBrowser;
}
