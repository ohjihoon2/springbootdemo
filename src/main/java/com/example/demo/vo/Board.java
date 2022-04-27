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
    private int attachFileIdx;
    private String deleteYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
