package com.example.demo.vo;

import lombok.Data;

@Data
public class AttachFile {
    private int idx;
    private String relatedTable;
    private int relatedIdx;
    private String originalName;
    private String saveName;
    private Long size;
    private String createId;
    private String createDate;
    private String deleteYn;
    private String deleteDate;
}
