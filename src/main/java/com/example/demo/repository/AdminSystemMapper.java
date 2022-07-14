package com.example.demo.repository;

import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
import com.example.demo.vo.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminSystemMapper")
public interface AdminSystemMapper {
    List<Map<String,Object>> findAllCodeGroup(Criteria criteria);

    CodeGroup findByIdxCodeGroup(int idx);

    List<Code> findAllByIdxCode(String codeGroupId);

    int insertCodeGroup(Map<String, Object> paramMap);

    int insertCode(Map<String, Object> paramMap);

    int updateCodeGroup(Map<String, Object> paramMap);

    int updateCode(ArrayList codeList);

    int existsCodeGroupId(Map<String,Object> paramMap);

    Map<String, Object> findSystemConfig();

    int updateSystemConfig(Map<String, Object> paramMap);
}
