package com.example.demo.service.impl;

import com.example.demo.repository.AdminUserMapper;
import com.example.demo.service.AdminUserService;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserMapper adminMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<Map<String, Object>> findAllUser(Criteria criteria) {
        return adminMapper.findAllUser(criteria);
    }

    @Override
    public int countUser(Criteria criteria) {
        return adminMapper.countUser(criteria);
    }

    @Override
    public User findByIdxUser(Map<String, Object> paramMap) {
        return adminMapper.findByIdxUser(paramMap);
    }

    @Override
    public int updateUser(Map<String, Object> paramMap) {
        return adminMapper.updateUser(paramMap);
    }

    @Override
    public int forceDeleteUser(Map<String, Object> paramMap) {
        return adminMapper.forceDeleteUser(paramMap);
    }

    @Override
    public int resetPassword(Map<String, Object> paramMap) {
        String resetPassword = adminMapper.getResetPassword();
        paramMap.put("userPwd",passwordEncoder.encode(resetPassword));

        return adminMapper.resetPassword(paramMap);
    }

    @Override
    public List<Map<String, Object>> findAllAdmin(Criteria criteria) {
        return adminMapper.findAllAdmin(criteria);
    }

    @Override
    public int countAdmin(Criteria criteria) {
        return adminMapper.countAdmin(criteria);
    }

    @Override
    public int updatePassword(Map<String, Object> paramMap) {
        int result = 0 ;


        String password = String.valueOf(paramMap.get("password"));
        String newPassword = String.valueOf(paramMap.get("newPassword"));

        paramMap.put("userPwd",passwordEncoder.encode(password));
        paramMap.put("newPwd",passwordEncoder.encode(newPassword));

        if( adminMapper.existsPassword(paramMap) == 1) {
            if(adminMapper.updatePassword(paramMap) == 1){
                result = 1;
            }
        }

        return result;
    }

    @Override
    public int insertAdmin(Map<String, Object> paramMap) {
        String password = String.valueOf(paramMap.get("password"));
        paramMap.put("userPwd",passwordEncoder.encode(password));
        return adminMapper.insertAdmin(paramMap);
    }

    @Override
    public int updateAdmin(Map<String, Object> paramMap) {
        return adminMapper.updateAdmin(paramMap);
    }

    @Override
    public String getResetPassword() {
        return adminMapper.getResetPassword();
    }
}
