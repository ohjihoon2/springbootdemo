package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BoardMaster {
    private int idx;
    private String boardId;
    private String boardNm;
    private String boardDesc;
    private String boardType;
    private String fileAttachYn;
    private String useYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
