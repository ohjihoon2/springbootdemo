package com.example.demo.service;

import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;

import java.util.List;
import java.util.Map;

public interface AdminSystemService {

    List<CodeGroup> findAllCodeGroup();

    CodeGroup findByCodeGroupIdCodeGroup(String CodeGroupId);

    List<Code> findAllByCodeGroupIdCode(String codeGroupId);

    int insertCodeGroup(Map<String, Object> paramMap);

    int updateCodeGroup(Map<String, Object> paramMap);
}
