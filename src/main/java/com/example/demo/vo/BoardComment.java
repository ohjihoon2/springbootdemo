package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BoardComment {
    private int idx;
    private int boardIdx;
    private Integer parentIdx;
    private Integer referenceIdx;
    private String commentContent;
    private String deleteYn;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
