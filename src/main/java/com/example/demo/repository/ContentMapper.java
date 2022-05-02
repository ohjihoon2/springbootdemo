package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository("ContentMapper")
public interface ContentMapper {

    Map<String, Object> findAllByContentId(Map<String, Object> paramMap);
}
