package com.example.demo.service;

import com.example.demo.vo.Board;
import com.example.demo.vo.Criteria;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {

    int insertBoard(MultipartFile[] files, Board board);

    List<Map<String,Object>> findByMasterIdxSearch(Criteria criteria);

    Board findAllByIdx(Map<String, Object> paramMap);
}
