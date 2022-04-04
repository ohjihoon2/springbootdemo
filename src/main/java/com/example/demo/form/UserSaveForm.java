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
    @NotBlank
    private String userPwdConfirm;
    @NotBlank
    private String userNm;
    private String userNicknm;
    @Email
    private String userEmail;
    private String userPhone;
    private String roleType;
    private String verificationCode;
    private String isVerified;
    private String useAt;
    private Date createDate;
}
