package com.example.demo.service.impl;

import com.example.demo.config.JasyptConfig;
import com.example.demo.repository.AdminSystemMapper;
import com.example.demo.service.AdminSystemService;
import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
import com.example.demo.vo.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminSystemServiceImpl implements AdminSystemService {

    private final AdminSystemMapper adminMapper;
    private final JasyptConfig jasyptConfig;

    @Override
    public List<Map<String,Object>> findAllCodeGroup(Criteria criteria) {
        return adminMapper.findAllCodeGroup(criteria);
    }

    @Override
    public CodeGroup findByIdxCodeGroup(int idx) {
        return adminMapper.findByIdxCodeGroup(idx);
    }

    @Override
    public List<Code> findAllByCodeGroupIdCode(String codeGroupId) {
        return adminMapper.findAllByCodeGroupIdCode(codeGroupId);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int insertCodeGroup(Map<String, Object> paramMap) {
        int result = 0;
        if(adminMapper.insertCodeGroup(paramMap) == 1){
            result = adminMapper.insertCode(paramMap);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int updateCodeGroup(Map<String, Object> paramMap) {
        int result = 0;
        if(adminMapper.updateCodeGroup(paramMap) == 1){
            result = adminMapper.updateCode(paramMap);
        }

        return result;
    }

    @Override
    public int existsCodeGroupId(Map<String,Object> paramMap) {
        return adminMapper.existsCodeGroupId(paramMap);
    }

    @Override
    public Map<String, Object> findSystemConfig() {
        Map<String, Object> systemConfig = adminMapper.findSystemConfig();
        String resetPassword = jasyptConfig.stringEncryptor().decrypt(systemConfig.get("resetPassword").toString());
        systemConfig.put("reset_password",resetPassword);
        return systemConfig;
    }

    @Override
    public int updateSystemConfig(Map<String, Object> paramMap) {
        paramMap.put("resetPassword",jasyptConfig.stringEncryptor().encrypt(paramMap.get("resetPassword").toString()));
        paramMap.put("mailPassword",jasyptConfig.stringEncryptor().encrypt(paramMap.get("mailPassword").toString()));
        return adminMapper.updateSystemConfig(paramMap);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int deleteCodeGroup(String codeGroupId) {
        int result = 0;
        if(adminMapper.deleteCodeGroup(codeGroupId) == 1){
            result = adminMapper.deleteCodeByCodeGroupId(codeGroupId);
        }

        return result;
    }

    @Override
    public int deleteCode(int idx) {
        return adminMapper.deleteCode(idx);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int updateCodeNm(Map<String, Object> paramMap) {
        return adminMapper.updateCodeNm(paramMap);
    }
}
