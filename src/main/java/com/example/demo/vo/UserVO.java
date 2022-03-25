package com.example.demo.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class UserVO {

    private int seq;
    private String userId;
    private String userPwd;
    private String userNm;
    private String userNicknm;
    private String userEmail;
    private String userPhone;
    private String roleType;
    private String verificationCode;
    private String isVerified;
    private String useAt;
    private Date createDate;
    private Date updateDate;
}
