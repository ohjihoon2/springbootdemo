package com.example.demo.service.impl;

import com.example.demo.repository.LoginMapper;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.util.RandomString;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;
    private final EmailService emailService;

    @Value("${site.name}")
    private String siteName;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        Users users = loginMapper.findByUserId(userId);
        if (users == null) {
            throw new UsernameNotFoundException(userId + "is not found.");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (users.getRoleType().equals("ROLE_ADMIN")) {
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
                Map<String, Object> map = new HashMap<>();
                map.put("userId", user.getUserId());
                map.put("userEmail", user.getUserEmail());
                return sendVerificationMail(request, map);
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
    public boolean updateVerificationCode(Map<String,Object> paramMap) {
        return loginMapper.updateVerificationCode(paramMap);
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
    public int sendVerificationMail(HttpServletRequest request, Map<String, Object> map) {
        int result = 0;
        String ranPw = RandomString.randomStr();
        String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String userId = map.get("userId").toString();
        String to = map.get("userEmail").toString();
        String subject = siteName +" 인증 처리";
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
            updateUserPwd(map);

            String email = users.getUserEmail();

            String to = email;
            String subject = siteName + " 임시 비밀번호 발급";
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
