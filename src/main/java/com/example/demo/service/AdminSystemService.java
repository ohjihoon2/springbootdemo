package com.example.demo.service;

import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
import com.example.demo.vo.Criteria;

import java.util.List;
import java.util.Map;

public interface AdminSystemService {

    List<Map<String,Object>> findAllCodeGroup(Criteria criteria);

    CodeGroup findByIdxCodeGroup(int idx);

    List<Code> findAllByIdxCode(String codeGroupId);

    int insertCodeGroup(Map<String, Object> paramMap);

    int updateCodeGroup(Map<String, Object> paramMap);

    int existsCodeGroupId(Map<String,Object> paramMap);

    Map<String, Object> findSystemConfig();

    int updateSystemConfig(Map<String, Object> paramMap);
}
