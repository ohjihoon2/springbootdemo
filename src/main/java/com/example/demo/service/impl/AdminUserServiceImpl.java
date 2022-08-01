package com.example.demo.service.impl;

import com.example.demo.config.JasyptConfig;
import com.example.demo.repository.AdminUserMapper;
import com.example.demo.service.AdminUserService;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserMapper adminMapper;
    private final JasyptConfig jasyptConfig;

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
    public Users findByIdxUser(Map<String, Object> paramMap) {
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
        String resetPassword = jasyptConfig.stringEncryptor().decrypt(SingletonData.getInstance().getConfigData().get("resetPassword").toString());
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
    public List<Map<String, Object>> findAllVisitor(Criteria criteria) {
        return adminMapper.findAllVisitor(criteria);
    }

    @Override
    public int countVisitor(Criteria criteria) {
        return adminMapper.countVisitor(criteria);
    }

    @Override
    public List<Map<String, Object>> findByStandardGraph(String standard) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(standard.equals("D")){
            mapList = adminMapper.findByStandardGraphDay();
        }else if(standard.equals("M")){
            mapList = adminMapper.findByStandardGraphMonth();
        }else if(standard.equals("Y")){
            mapList = adminMapper.findByStandardGraphYear();
        }
        return mapList;
    }

    @Override
    public List<Map<String, Object>> findByStandardPie(String standard) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        if(standard.equals("D")){
            mapList = adminMapper.findByStandardPieDay();
        }else if(standard.equals("M")){
            mapList = adminMapper.findByStandardPieMonth();
        }else if(standard.equals("Y")){
            mapList = adminMapper.findByStandardPieYear();
        }
        return mapList;
    }
}
