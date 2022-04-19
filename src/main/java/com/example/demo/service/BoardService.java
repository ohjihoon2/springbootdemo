package com.example.demo.service;

import com.example.demo.vo.Board;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BoardService {

    int insertBoard(Map<String, Object> paramMap);

    int insertBoard(MultipartFile[] files, Map<String, Object> paramMap);

    List<Board> findByMasterIdxSearch(Map<String, Object> paramMap);

    Board findAllByIdx(String idx);
}
