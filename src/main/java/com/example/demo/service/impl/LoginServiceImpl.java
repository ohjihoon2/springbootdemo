package com.example.demo.service.impl;

import com.example.demo.repository.CommonMapper;
import com.example.demo.repository.LoginMapper;
import com.example.demo.service.CommonService;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.SingletonData;
import com.example.demo.vo.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final CommonService commonServive;
    private final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        Users users = loginMapper.findByUserId(userId);
        if (users == null) {
            throw new UsernameNotFoundException(userId + "is not found.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (users.getRoleType().equals("ROLE_SYSTEM")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SYSTEM"));
            users.setRoleType("ROLE_SYSTEM");
        }else if (users.getRoleType().equals("ROLE_ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            users.setRoleType("ROLE_ADMIN");
        }else if (users.getRoleType().equals("ROLE_MANAGER")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            users.setRoleType("ROLE_MANAGER");
        }else if (users.getRoleType().equals("ROLE_USER")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            users.setRoleType("ROLE_USER");
        }
        return new org.springframework.security.core.userdetails.User(users.getUserId(), users.getUserPwd(), authorities);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int userSave(Users user, HttpServletRequest request) {

        int cnt = loginMapper.countByUserId(user.getUserId());

        if(cnt == 0){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
            user.setDeleteYn("N");
            if(loginMapper.saveUser(user) == 1){
                Map<String, Object> mailMap = new HashMap<>();
                String ranPw = RandomString.randomStr();
                String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");
                String userId = mailMap.get("userId").toString();
                String subject = SingletonData.getInstance().getConfigData().get("homepageName") +" 인증 처리";

                String text = "안녕하세요.<br>" +
                        "하단에 링크 클릭 시 인증 처리 됩니다. <br>" +
                        "<b><a href=\""+domain+"/verifyMail?userId="+userId+"&code="+ranPw+"\">링크</a></b> 입니다.";

                mailMap.put("ranPw", ranPw);
                mailMap.put("userId", userId);
                mailMap.put("userEmail", user.getUserEmail());
                mailMap.put("subject",subject);
                mailMap.put("text",text);

                return commonServive.sendVerificationMail(request, mailMap);
            }

        }

        return 0;
    }

    @Override
    public Users findByEmailAndUserNm(Map<String, Object> paraMap) {
        String email = (String) paraMap.get("email");
        String userNm = (String) paraMap.get("userNm");

        Users users = loginMapper.findByEmailAndUserNm(email,userNm);

        return users;
    }

    @Override
    public Users findByUserNmAndUserId(String userNm, String userId) {
        Users users = loginMapper.findByUserNmAndUserId(userNm,userId);

        return users;
    }

    @Override
    public void updateUserPwd(Map<String, Object> map) {
        loginMapper.updateUserPwd(map);
    }

    @Override
    public Users checkUserByUserId(String userId) {
        return loginMapper.findByUserId(userId);
    }

    @Override
    public Map<String, Object> findUserNicknmVerificationYnEmailIdxByUserId(String userId) {
        return loginMapper.findUserNicknmVerificationYnEmailIdxByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int forgetPwd(String userNm, String userId) {
        int result =0;
        Users users = findByUserNmAndUserId(userNm,userId);

        if(users != null) {

            String ranPw = RandomString.randomStrSp();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            users.setUserPwd(passwordEncoder.encode(ranPw));
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("userPwd",passwordEncoder.encode(ranPw));

            String email = users.getUserEmail();

            String to = email;
            String subject = SingletonData.getInstance().getConfigData().get("homepageName") + " 임시 비밀번호 발급";
            String text = "안녕하세요.<br>" +
                    "요청하신 이메일 주소로 임시 비밀번호를 발급하였습니다.<br>" +
                    "비밀번호는 <b>[" + ranPw + "]</b> 입니다. ";

            if (emailService.sendMail(to, subject, text)) {
                result = 1;
                updateUserPwd(map);
            } else {
                result = 0;
            }
        }else{
            result=2;
        }

        return result;
    }

    @Override
    public void updateLastLoginDate(String userId) {
        loginMapper.updateLastLoginDate(userId);
    }

    @Override
    public int reSendVerificationMail(HttpServletRequest request, Map<String, Object> map) {
        Map<String,Object> mailMap = new HashMap<>();
        String ranPw = RandomString.randomStr();
        String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String userId = map.get("userId").toString();

        String subject = SingletonData.getInstance().getConfigData().get("homepageName") +" 이메일 인증";
        String text = "안녕하세요.<br>" +
                "하단에 링크 클릭 시 인증 처리 됩니다. <br>" +
                "<b><a href=\""+domain+"/verifyMail?userId="+userId+"&code="+ranPw+"\">링크</a></b> 입니다.";

        mailMap.put("ranPw", ranPw);
        mailMap.put("userId", userId);
        mailMap.put("userEmail", map.get("userEmail"));
        mailMap.put("subject",subject);
        mailMap.put("text",text);

        return commonServive.sendVerificationMail(request, mailMap);
    }

    @Override
    public int otherVerificationMail(HttpServletRequest request, Map<String, Object> map) {
        int result = 0;
        Map<String,Object> mailMap = new HashMap<>();
        String ranPw = RandomString.randomStr();
        String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String userId = map.get("userId").toString();
        String userEmail = map.get("userEmail").toString();

        String subject = SingletonData.getInstance().getConfigData().get("homepageName") +" 이메일 변경 인증";
        String text = "안녕하세요.<br>" +
                "하단에 링크 클릭 시 인증 처리 됩니다. <br>" +
                "<b><a href=\""+domain+"/verifyMail?userId="+userId+"&code="+ranPw+"\">링크</a></b> 입니다.";

        mailMap.put("ranPw", ranPw);
        mailMap.put("userId", userId);
        mailMap.put("userEmail", userEmail);
        mailMap.put("subject",subject);
        mailMap.put("text",text);

        if(commonServive.sendVerificationMail(request, mailMap) == 1){
            result = loginMapper.updateEmailOnly(map);
        }

        return result;
    }

}
