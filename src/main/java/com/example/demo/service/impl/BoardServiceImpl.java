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
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private FileUtils fileUtils;

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Override
    public int insertBoard(Map<String, Object> paramMap) {
        return boardMapper.insertBoard(paramMap);
    }

    @Override
    public int insertBoard(MultipartFile[] files, Map<String, Object> paramMap) {
        int result = 0;

        if (insertBoard(paramMap) != 1) {
            return result;
        }


        List<AttachFile> fileList = fileUtils.uploadFiles(files, paramMap);
        if (CollectionUtils.isEmpty(fileList) == false) {
            result = fileMapper.insertAttachFile(fileList);
            if (result < 1) {
                result = 0;
            }
        }
        return result;
    }

    @Override
    public List<Board> findByMasterIdxSearch(Map<String, Object> paramMap) {
        return boardMapper.findByMasterIdxSearch(paramMap);
    }

    @Override
    public Board findAllByIdx(String idx) {
        return boardMapper.findAllByIdx(idx);
    }
}