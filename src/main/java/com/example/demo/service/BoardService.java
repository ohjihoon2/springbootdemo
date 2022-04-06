package com.example.demo.service;

import com.example.demo.vo.Board;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    int insertBoard(Board board);
    int insertBoard(MultipartFile[] files, Board board, String boardType, int masterIdx);
}
