package com.example.demo.service.impl;

import com.example.demo.form.UserSaveForm;
import com.example.demo.repository.LoginMapper;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        User user = loginMapper.findByUserId(userId);
        if (user == null) {
            throw new UsernameNotFoundException(userId + "is not found.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (user.getRoleType().equals("ROLE_ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            user.setRoleType("ROLE_ADMIN");
        } else if (user.getRoleType().equals("ROLE_USER")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            user.setRoleType("ROLE_USER");
        } else if (user.getRoleType().equals("ROLE_GENERAL")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_GENERAL"));
            user.setRoleType("ROLE_GENERAL");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getUserPwd(), authorities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int userSave(UserSaveForm userSaveForm,HttpServletRequest request) {

        int cnt = loginMapper.countByUserId(userSaveForm.getUserId());

        if(cnt == 0){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userSaveForm.setUserPwd(passwordEncoder.encode(userSaveForm.getUserPwd()));
            userSaveForm.setUseYn("Y");
            if(loginMapper.saveUser(userSaveForm) == 1){
                Map<String, Object> map = new HashMap<>();
                map.put("userId",userSaveForm.getUserId());
                map.put("userEmail",userSaveForm.getUserEmail());
                System.out.println("map = " + map);
                return sendVerificationMail(request, map);
            }

        }

        return 0;
    }

    @Override
    public User findByEmailAndUserNm(Map<String, Object> paraMap) {
        String email = (String) paraMap.get("email");
        String userNm = (String) paraMap.get("userNm");

        User user = loginMapper.findByEmailAndUserNm(email,userNm);

        return user;
    }

    @Override
    public User findByUserNmAndUserId(String userNm, String userId) {
        User user = loginMapper.findByUserNmAndUserId(userNm,userId);

        return user;
    }

    @Override
    public void updateUserPwd(Map<String, Object> map) {
        loginMapper.updateUserPwd(map);
    }

    @Override
    public User checkUserByUserId(String userId) {
        return loginMapper.findByUserId(userId);
    }

    @Override
    public String findUserNicknmByUserId(String userId) {
        return loginMapper.findUserNicknmByUserId(userId);
    }

    @Override
    public boolean updateVerificationCode(Map<String,Object> paramMap) {
        return loginMapper.updateVerificationCode(paramMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> verifyMail(String userId, String code) {
        Map<String, Object> resultMap = new HashMap<>();
        int result = 0;
        String msg = "";
        //VerificationCode 일치
        if(loginMapper.findVerificationCodeByUserIdCode(userId, code) == 1){
            // verificationYn 업데이트
            if(loginMapper.updateverificationYn(userId,code) == 1){
                //성공
                result = 1;
                msg = "Success";
            }else{
                //실패
                result = 0;
                msg = "Update fail";
            }
        }else{
            //verificationCode 불일치
            result = 0;
            msg = "Not found verification code";
        }

        resultMap.put("result", result);
        resultMap.put("msg", msg);
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int sendVerificationMail(HttpServletRequest request, Map<String, Object> map) {
        int result = 0;
        String ranPw = RandomString.randomStr();
        String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String userId = map.get("userId").toString();
        String to = map.get("userEmail").toString();
        String subject = "OO 인증 처리";
        String text = "안녕하세요.<br>" +
                "하단에 링크 클릭 시 인증 처리 됩니다. <br>" +
                "<b><a href=\""+domain+"/verifyMail?userId="+userId+"&code="+ranPw+"\">링크</a></b> 입니다.";

        boolean res = emailService.sendMail(to, subject, text);

        Map<String,Object> paraMap = new HashMap<>();

        paraMap.put("VerificationCode",ranPw);
        paraMap.put("userId",userId);

        if (res) {
            if(updateVerificationCode(paraMap)){
                result = 1;
            }else {
                result = 0;
            }
        } else {
            result = 0;
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int forgetPwd(String userNm, String userId) {
        int result =0;
        User user = findByUserNmAndUserId(userNm,userId);

        if(user != null) {

            String ranPw = RandomString.randomStrSp();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setUserPwd(passwordEncoder.encode(ranPw));
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("userPwd",passwordEncoder.encode(ranPw));
            updateUserPwd(map);

            String email = user.getUserEmail();

            String to = email;
            String subject = "OO 임시 비밀번호 발급";
            String text = "안녕하세요.<br>" +
                    "요청하신 이메일 주소로 임시 비밀번호를 발급하였습니다.<br>" +
                    "비밀번호는 <b>[" + ranPw + "]</b> 입니다. ";

            if (emailService.sendMail(to, subject, text)) {
                result = 1;
            } else {
                result = 0;
            }
        }else{
            result=2;
        }

        return result;
    }

    @Override
    public int checkUserByUserNicknm(String userNicknm) {
        return loginMapper.existByUserNicknm(userNicknm);
    }

    @Override
    public int checkUserByUserEmail(String userEmail) {
        return loginMapper.existByUserEmail(userEmail);
    }
}
