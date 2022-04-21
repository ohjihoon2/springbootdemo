package com.example.demo.service.impl;

import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.FileMapper;
import com.example.demo.service.BoardService;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.AttachFileMaster;
import com.example.demo.vo.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private FileUtil fileUtil;

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Override
    public int insertBoard(Board board) {
        return boardMapper.insertBoard(board);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int insertBoard(MultipartFile[] files, Board board) {
        int result = 0;

        List<AttachFile> fileList = fileUtil.uploadFiles(files, board.getCreateIdx());
        if (CollectionUtils.isEmpty(fileList) == false) {
            AttachFileMaster attachFileMaster = new AttachFileMaster();
            fileMapper.insertAttachFileMaster(attachFileMaster);
            int idx = attachFileMaster.getIdx();
            for (AttachFile attachFile : fileList) {
                attachFile.setIdx(idx);
            }
            result = fileMapper.insertAttachFile(fileList);
            if (result < 1) {
                result = 0;
            }
            board.setAttachFileIdx(idx);
        }

        if (insertBoard(board) != 1) {
            return result;
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