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

    private final FileUtil fileUtil;

    private final BoardMapper boardMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int insertBoard(MultipartFile[] files, Board board) {
        int result = 0;

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, board.getCreateIdx());

            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            board.setAttachFileIdx(idx);
        }

        if (boardMapper.insertBoard(board) == 1) {
            result = 1;
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