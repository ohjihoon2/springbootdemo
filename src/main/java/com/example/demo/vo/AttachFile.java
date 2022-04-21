package com.example.demo.vo;

import lombok.Data;

@Data
public class AttachFile {
    private int idx;
    private int fileSn;
    private String originalName;
    private String saveName;
    private Long size;
    private int createIdx;
    private String createDate;
    private String deleteYn;
    private String deleteDate;
}
