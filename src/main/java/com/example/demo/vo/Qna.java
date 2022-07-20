package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class Qna {
    private int idx;
    private Integer parentIdx;
    private String qaSubject;
    private String qaContent;
    private int attachFileIdx;
    private int createIdx;
    private Date createDate;
    private int updateIdx;
    private Date updateDate;
}
