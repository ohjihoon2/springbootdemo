package com.example.demo.service.impl;

import com.example.demo.repository.BoardMapper;
import com.example.demo.repository.FileMapper;
import com.example.demo.service.BoardService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.HitCookie;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final FileUtil fileUtil;

    private final BoardMapper boardMapper;

    private final FileMapper fileMapper;

    private final ApplicationEventPublisher eventPublisher;

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

        if(thumb != null){
            board.setThumbnailNm(thumb.getOriginalFilename());
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
    public int updateBoard(MultipartFile[] files,MultipartFile thumb, Board board) {

        int result = 0;

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, board.getAttachFileIdx(), board.getUpdateIdx());

            // DB에 파일 저장
            board.setAttachFileIdx(fileUtil.updateFile(fileList,board.getAttachFileIdx()));
        }

        if(boardMapper.updateBoard(board) != 0){
            // 썸네일 파일 저장
            if(thumb != null){
                fileUtil.uploadThumbnail(thumb, board.getIdx());
            }
            result = 1;
        }

        return result;
    }

    @Override
    public int deleteBoardUser(Map<String, Object> paramMap) {
        return boardMapper.deleteBoardUser(paramMap);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int deleteAllBoardAdmin(Map<String, Object> paramMap) {
        int result = 0;

        List<Map<String, Object>> boardDetailList = boardMapper.findAllByIdxList(paramMap);

        for (Map<String, Object> boardDetail : boardDetailList) {
            if(boardDetail.get("thumbnailNm") != null){
                //썸네일 삭제
                fileUtil.deleteThumbnailFile(paramMap.get("idx").toString());
            }
            if(boardDetail.get("attachFileIdx") != null) {
                int attachFileIdx = Integer.parseInt(boardDetail.get("attachFileIdx").toString());
                deleteByIdxFile(attachFileIdx);
            }
        }

        if(boardMapper.deleteAllBoardAdmin(paramMap) != 0){
            result = 1;
        }
        return result;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int deleteOneBoardAdmin(Map<String, Object> paramMap) {
        int result = 0;

        Map<String, Object> boardDetail = boardMapper.findAllByIdx(paramMap);

        if(boardDetail.get("thumbnailNm") != null){
            //썸네일 삭제
            fileUtil.deleteThumbnailFile(paramMap.get("idx").toString());
        }

        if(boardDetail.get("attachFileIdx") != null) {

            int attachFileIdx = Integer.parseInt(boardDetail.get("attachFileIdx").toString());

            deleteByIdxFile(attachFileIdx);

        }

        if(boardMapper.deleteOneBoardAdmin(paramMap) != 0){
            result = 1;
        }

        // 게시글 댓글 전체 삭제
        boardMapper.deleteBoardCommentAll(paramMap);


        return result;
    }

    @Override
    public List<AttachFile> findAllByIdxAttachFile(int idx) {

        // board에는 attachFileIdx가 초기화 되지 않기 때문에 파일이 존재하는지 확인
        Board board = boardMapper.findAttachFileIdxByIdxBoard(idx);

        return boardMapper.findAllByAttachFileIdx(board.getAttachFileIdx());

    }

    @Override
    public int moveOneBoard(Map<String, Object> paramMap) {
        return boardMapper.moveOneBoard(paramMap);
    }

    @Override
    public List<Map<String, Object>> moreBoardComment(int idx) {
        return boardMapper.moreBoardComment(idx);
    }

    @Override
    public int updateThumbnailNmByIdx(String idx) {
        return boardMapper.updateThumbnailNmByIdx(idx);
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
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
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
    public int deleteBoardCommentAdmin(Map<String, Object> paramMap) {
       return boardMapper.deleteBoardCommentAdmin(paramMap);
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
    public Map<String,Object> findAllByIdx(Map<String, Object> paramMap) {
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

    /**
     * 실제파일, DB에 저장된 파일 삭제
     * @param attachFileIdx
     */
    private void deleteByIdxFile(int attachFileIdx) {
        List<Map<String,Object>> saveNameList = fileMapper.findSaveNameByAttachFileIdx(attachFileIdx);

        // 실제 파일 삭제
        for (Map<String, Object> map : saveNameList) {
            fileUtil.deleteRealFile(map.get("saveName").toString());
        }

        // DB 파일 삭제
        fileMapper.deleteByIdxAttachFile(attachFileIdx);
        fileMapper.deleteAttachFileMaster(attachFileIdx);
    }

}