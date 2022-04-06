package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Board {
    private int idx;
    private int masterIdx;
    private String boardSubject;
    private String boardContent;
    private int hit;
    private Date startDate;
    private Date endDate;
    private String useYn;
    private String createId;
    private Date createDate;
    private String updateId;
    private Date updateDate;
}
