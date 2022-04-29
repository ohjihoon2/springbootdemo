package com.example.demo.service.impl;

import com.example.demo.repository.BoardMapper;
import com.example.demo.service.BoardService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.HitCookie;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final FileUtil fileUtil;

    private final BoardMapper boardMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int insertBoard(MultipartFile[] files,MultipartFile thumb, Board board) {
        int result = 0;

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, 0,board.getCreateIdx());

            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            board.setAttachFileIdx(idx);
        }

        if(boardMapper.insertBoard(board) != 0){
            // 썸네일 파일 저장
            if(thumb != null){
                fileUtil.uploadThumbnail(thumb, board.getIdx());
            }
            result = 1;
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int updateBoard(MultipartFile[] files, Board board) {

        int result = 0;

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, board.getAttachFileIdx(), board.getUpdateIdx());

            // DB에 파일 저장
            board.setAttachFileIdx(fileUtil.updateFile(fileList,board.getAttachFileIdx()));
        }

        result = boardMapper.updateBoard(board);

        return result;
    }

    @Override
    public int deleteBoardUser(Map<String, Object> paramMap) {
        return boardMapper.deleteBoardUser(paramMap);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int deleteBoardAdmin(Map<String, Object> paramMap) {
        int result = 0;
        if(boardMapper.deleteBoardAdmin(paramMap) != 0){
            boardMapper.deleteBoardCommentWithBoard(paramMap);
            result = 1;
        }

        return result;
    }

    @Override
    public BoardMaster findAllByIdxBoardMaster(Map<String, Object> paramMap) {

        return boardMapper.findAllByIdxBoardMaster(paramMap);
    }

    @Override
    public int moveBoards(Map<String, Object> paramMap) {
        return boardMapper.moveBoards(paramMap);
    }

    @Override
    public void addHit(HttpServletRequest request, HttpServletResponse response, int idx) {
        String formatIdx = String.format("%09d", idx);

        Cookie accumulateIdxCookie = Arrays
                .stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals("alreadyViewIdx"))
                .findFirst()
                .orElseGet(() -> {
                    Cookie cookie = HitCookie.createAccIdxCookie(formatIdx);    // 조회수 중복 방지용 쿠키 생성
                    response.addCookie(cookie);                        // 생성한 쿠키를 response에 담는다.
                    boardMapper.incrementBoardHit(idx);            // 조회수 증가 쿼리 수행
                    return cookie;
                });

        // 서로 다른 idx에 대해서는 "/" 로 구분한다.
        String cookieValue = accumulateIdxCookie.getValue();

        if(cookieValue.contains(formatIdx) == false) {
            String newCookieValue = cookieValue + "/" + formatIdx;
            response.addCookie(HitCookie.createAccIdxCookie(newCookieValue));    // 기존에 같은 이름의 쿠키가 있다면 덮어쓴다.
            boardMapper.incrementBoardHit(idx);                        // 조회수 증가 쿼리 수행
        }

    }

    @Override
    public List<Map<String, Object>> findNoticeByBoardIdBoard(Criteria criteria) {
        return boardMapper.findNoticeByBoardIdBoard(criteria);
    }

    @Override
    public List<Map<String, Object>> findAllByIdxBoardComment(Map<String, Object> paramMap) {
        return boardMapper.findAllByIdxBoardComment(paramMap);
    }

    @Override
    public int insertBoardComment(BoardComment comment) {
        return boardMapper.insertBoardComment(comment);
    }

    @Override
    public int updateBoardComment(BoardComment comment) {
        return boardMapper.updateBoardComment(comment);
    }

    @Override
    public int deleteBoardCommentUser(BoardComment comment) {
        return boardMapper.deleteBoardCommentUser(comment);
    }
    @Override
    public int deleteBoardCommentAdmin(BoardComment comment) {
       return boardMapper.deleteBoardCommentAdmin(comment);
    }

    @Override
    public List<Map<String, Object>> findBoardMaster() {
       return boardMapper.findBoardMaster();
    }

    @Override
    public List<Map<String,Object>> findAllByBoardIdBoard(Criteria criteria) {
        return boardMapper.findAllByBoardIdBoard(criteria);
    }

    @Override
    public Board findAllByIdx(Map<String, Object> paramMap) {
        return boardMapper.findAllByIdx(paramMap);
    }

    @Override
    public Map<String, Object> findByBoardIdBoardMaster(String boardId) {
        return boardMapper.findByBoardIdBoardMaster(boardId);
    }

    @Override
    public int countByBoardIdBoard(Criteria criteria) {
        return boardMapper.countByBoardIdBoard(criteria);
    }


}