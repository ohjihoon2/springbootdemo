package com.example.demo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class MenuTree {
    private int idx;
    private int parentIdx;
    private int lvl;
    private String name;
    private String link;
    private String sortOrder;
    private String writeId;
    private Date createDate;
    private String openYn;
}
