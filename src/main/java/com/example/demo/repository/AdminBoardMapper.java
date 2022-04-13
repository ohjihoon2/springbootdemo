package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminBoardMapper")
public interface AdminBoardMapper {


    List<Map<String,Object>> findAllBoardMaster(Criteria criteria);

    int insertBoardMaster(Map<String, Object> paramMap);

    int existsBoardId(Map<String,Object> paramMap);

    int updateBoardMaster(Map<String, Object> paramMap);

    BoardMaster findByIdxBoardMaster(int idx);

    int deleteBoardMaster(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllContent(Criteria criteria);

    Content findByIdxContent(int idx);

    int insertContent(Map<String, Object> paramMap);

    int existsContentId(Map<String, Object> paramMap);

    int updateContent(Map<String, Object> paramMap);

    int deleteContent(Map<String, Object> paramMap);

}
