package com.example.demo.repository;

import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminSystemMapper")
public interface AdminSystemMapper {
    List<CodeGroup> findAllCodeGroup();

    CodeGroup findByCodeGroupIdCodeGroup(String CodeGroupId);

    List<Code> findAllByCodeGroupIdCode(String codeGroupId);

    int insertCodeGroup(Map<String, Object> paramMap);

    int insertCode(Map<String, Object> paramMap);

    int updateCodeGroup(Map<String, Object> paramMap);

    int updateCode(ArrayList codeList);
}
