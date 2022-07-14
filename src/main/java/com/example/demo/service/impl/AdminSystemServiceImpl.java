package com.example.demo.service.impl;

import com.example.demo.repository.AdminSystemMapper;
import com.example.demo.service.AdminSystemService;
import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
import com.example.demo.vo.Criteria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminSystemServiceImpl implements AdminSystemService {

    private final AdminSystemMapper adminMapper;

    @Override
    public List<Map<String,Object>> findAllCodeGroup(Criteria criteria) {
        return adminMapper.findAllCodeGroup(criteria);
    }

    @Override
    public CodeGroup findByIdxCodeGroup(int idx) {
        return adminMapper.findByIdxCodeGroup(idx);
    }

    @Override
    public List<Code> findAllByIdxCode(String codeGroupId) {
        return adminMapper.findAllByIdxCode(codeGroupId);
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
            ArrayList codeList = (ArrayList) paramMap.get("codeList");
            result = adminMapper.updateCode(codeList);
        }

        return result;
    }

    @Override
    public int existsCodeGroupId(Map<String,Object> paramMap) {
        return adminMapper.existsCodeGroupId(paramMap);
    }

    @Override
    public Map<String, Object> findSystemConfig() {
        return adminMapper.findSystemConfig();
    }

    @Override
    public int updateSystemConfig(Map<String, Object> paramMap) {
        return adminMapper.updateSystemConfig(paramMap);
    }
}
