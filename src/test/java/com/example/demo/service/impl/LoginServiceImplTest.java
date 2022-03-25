package com.example.demo.service.impl;

import com.example.demo.repository.LoginMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@Rollback(false)
@Transactional
@Slf4j
class LoginServiceImplTest {
    @Autowired
    private LoginMapper loginMapper;
/*

    @Test
    public void ADMIN_생성() {
        String userId = "admin";
        String userPwd ="1234";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Map<String,Object> map = new HashMap<>();

        map.put("userId",userId);
        map.put("userPwd",passwordEncoder.encode(userPwd));
        log.debug("map = {}",map);
        int result = loginMapper.saveAdminTest(map);

        log.debug(String.valueOf(result));

    }
*/

    @Test
    public void USER_생성() {
        String userId = "USER";
        String userPwd ="1234";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Map<String,Object> map = new HashMap<>();

        map.put("userId",userId);
        map.put("userPwd",passwordEncoder.encode(userPwd));
        log.debug("map = {}",map);
        int result = loginMapper.saveUserTest(map);

        log.debug(String.valueOf(result));

    }

    @Test
    public void 비밀번호_바꾸기() {
        String userId = "test";
        String userPwd ="1234";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        Map<String,Object> map = new HashMap<>();

        map.put("userId",userId);
        map.put("userPwd",passwordEncoder.encode(userPwd));
        loginMapper.updateUserPwd(map);

    }
}