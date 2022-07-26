package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Banner {
    private int idx;
    private String bnrSubject;
    private Integer webAttachFileIdx;
    private Integer mobileAttachFileIdx;
    private String bnrFirstId;
    private String bnrSecondId;
    private String bnrPosition;
    private String startDate;
    private String endDate;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
