package com.example.demo.service.impl;

import com.example.demo.repository.AdminSystemMapper;
import com.example.demo.service.AdminSystemService;
import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
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
    public List<CodeGroup> findAllCodeGroup() {
        return adminMapper.findAllCodeGroup();
    }

    @Override
    public CodeGroup findByCodeGroupIdCodeGroup(String CodeGroupId) {
        return adminMapper.findByCodeGroupIdCodeGroup(CodeGroupId);
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
            ArrayList codeList = (ArrayList) paramMap.get("codeList");
            result = adminMapper.updateCode(codeList);
        }

        return result;
    }
}
