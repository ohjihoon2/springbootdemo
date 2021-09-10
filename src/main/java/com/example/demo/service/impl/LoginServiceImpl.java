package com.example.demo.service.impl;

import com.example.demo.form.UserSaveForm;
import com.example.demo.repository.LoginMapper;
import com.example.demo.service.LoginService;
import com.example.demo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginMapper loginMapper;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        UserVO userVO = loginMapper.findByUserId(userId);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (userVO.getRoleType().equals("ROLE_SUPER")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUPER"));
            userVO.setRoleType("ROLE_SUPER");
        } else if (userVO.getRoleType().equals("ROLE_MAIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MAIN"));
            userVO.setRoleType("ROLE_MAIN");
        } else if (userVO.getRoleType().equals("ROLE_SUB")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_SUB"));
            userVO.setRoleType("ROLE_SUB");
        } else if (userVO.getRoleType().equals("ROLE_GENERAL")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_GENERAL"));
            userVO.setRoleType("ROLE_GENERAL");
        }

        return new User(userVO.getUserId(), userVO.getUserPwd(), authorities);
    }

    @Override
    public int userSave(UserSaveForm userSaveForm) {
        userSaveForm.setRoleType("ROLE_MAIN");

        int cnt = loginMapper.countByUserId(userSaveForm.getUserId());

        if(cnt == 0){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userSaveForm.setUserPwd(passwordEncoder.encode(userSaveForm.getUserPwd()));
            userSaveForm.setUseAt("Y");
            return loginMapper.saveUser(userSaveForm);
        }

        return 0;
    }

    @Override
    public UserVO findByEmailAndUserNm(Map<String, Object> paraMap) {
        String email = (String) paraMap.get("email");
        String userNm = (String) paraMap.get("userNm");

        UserVO userVO = loginMapper.findByEmailAndUserNm(email,userNm);

        return userVO;
    }

    @Override
    public UserVO findByUserNmAndUserId(String userNm, String userId) {
        UserVO userVO = loginMapper.findByUserNmAndUserId(userNm,userId);

        return userVO;
    }

    @Override
    public void updateUserPwd(Map<String, Object> map) {
        loginMapper.updateUserPwd(map);
    }

    @Override
    public UserVO checkUserByUserId(String userId) {
        return loginMapper.findByUserId(userId);
    }
}
