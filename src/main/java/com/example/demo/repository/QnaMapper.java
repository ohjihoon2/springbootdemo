package com.example.demo.repository;

import com.example.demo.vo.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("QnaMapper")
public interface QnaMapper {

    int countByQna(Criteria criteria);

    List<Map<String,Object>> findAllQna(Criteria criteria);

    Map<String, Object> findByIdxQnaConfig(int idx);

    List<Map<String, Object>> findByIdxQna(int idx);
}
