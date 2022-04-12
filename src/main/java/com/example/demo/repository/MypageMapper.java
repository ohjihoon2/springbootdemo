package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository("MypageMapper")
public interface MypageMapper {

    int withdrawUser(Map<String, Object> paramMap);
}
