package com.example.demo.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class User {

    private int idx;
    private String userId;
    private String userPwd;
    private String userNm;
    private String userNicknm;
    private String userEmail;
    private String userPhone;
    private String roleType;
    private String adminMemo;
    private String verificationCode;
    private String verificationYn;
    private String marketingYn;
    private String useYn;
    private Date createDate;
    private Date updateDate;
    private String deleteYn;
}
