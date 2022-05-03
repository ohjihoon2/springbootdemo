package com.example.demo.repository;

import com.example.demo.vo.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("FaqMapper")
public interface FaqMapper {

    Map<String, Object> findFaqNmFaqMaster();

    List<Map<String,Object>> findAllFaq(Criteria criteria);

    Map<String, Object> findAllByIdx(Criteria criteria);

    int countByFaq(Criteria criteria);

    int countByIdxFaq(Criteria criteria);
}
