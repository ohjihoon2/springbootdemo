package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository("AlertMapper")
public interface AlertMapper {

    int countReadYn(int userIdx);
}
