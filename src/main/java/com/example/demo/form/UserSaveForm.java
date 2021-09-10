package com.example.demo.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @Email
    private String userEmail;
    private String userPhone;
    private String authNo;
    private String bookmark;
    private String roleType;
    private String jobPosition;
    private String depart;
    private String withdrawalAt;
    private String useAt;
    private Date createDate;
}
