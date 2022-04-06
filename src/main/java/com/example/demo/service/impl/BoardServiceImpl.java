package com.example.demo.service.impl;

import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.FileMapper;
import com.example.demo.service.BoardService;
import com.example.demo.util.FileUtils;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private FileUtils fileUtils;

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Override
    public int insertBoard(Board board) {
        return boardMapper.insertBoard(board);
    }

    @Override
    public int insertBoard(MultipartFile[] files, Board board, String boardType, int masterIdx) {
        int result = 0;

        if (insertBoard(board) != 1) {
            return result;
        }

        List<AttachFile> fileList = fileUtils.uploadFiles(files, masterIdx, boardType);
        if (CollectionUtils.isEmpty(fileList) == false) {
            result = fileMapper.insertAttachFile(fileList);
            if (result < 1) {
                result = 0;
            }
        }
        return result;
    }
}