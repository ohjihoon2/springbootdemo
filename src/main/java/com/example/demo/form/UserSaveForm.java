package com.example.demo.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserSaveForm {
    @NotBlank
    private String userId;
    @NotBlank
    private String userPwd;
    private String userPwdChk;
    @NotBlank
    private String userNm;
    private String userNicknm;
    @Email
    private String userEmail;
    private String userPhone;
    private String roleType;
    private String verificationCode;
    private String verificationYn;
    private String useYn;
    private Date createDate;
}
