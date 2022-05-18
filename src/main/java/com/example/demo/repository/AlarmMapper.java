package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository("AlarmMapper")
public interface AlarmMapper {

    int countReadYn(int userIdx);
}
