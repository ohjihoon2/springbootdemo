package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.util.FileUtil;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;
    private final FileUtil fileUtil;


    /**
     * 게시물 리스트
     * @param boardId
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/{boardId}")
    public String boardList(@PathVariable("boardId") String boardId, @ModelAttribute Criteria criteria
                            , HttpServletResponse response, HttpServletRequest request, Model model, Device device) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardId", boardId);
        criteria.setParamMap(paramMap);
        Map<String,Object> boardMaster = boardService.findByBoardIdBoardMaster(boardId);

        int webPageCount = DeviceCheck.getPageCount(device);
        String boardType = String.valueOf(boardMaster.get("boardType"));
        int contentCount = DeviceCheck.getContentCount(boardType, device);

        criteria.setAmount(contentCount);

        List<Map<String,Object>> noticeList = new ArrayList<>();
        if(criteria.getPageNum() == 1 && criteria.getSearchKeyword() == null){
            noticeList = boardService.findNoticeByBoardIdBoard(criteria);
        }

        List<Map<String,Object>> boardList = boardService.findAllByBoardIdBoard(criteria);

        int total = boardService.countByBoardIdBoard(criteria);

        Page pageMaker = new Page(total, webPageCount, criteria);

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("boardList", boardList);
        model.addAttribute("boardMaster", boardMaster);

        return "/board/boardList";
    }

    /**
     * 게시물 삭제(다중)
     * @param boardId
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/{boardId}")
    @ResponseBody
    public Map<String,Object> deleteBoards(@PathVariable("boardId") String boardId, @RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {
        paramMap.put("boardId", boardId);
        int result = boardService.deleteAllBoardAdmin(paramMap);
        return ResultStr.set(result);
    }

    /**
     * 게시판 목록
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardMaster")
    @ResponseBody
    public List<Map<String, Object>> boardMasterList(HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, Object>> resultList = boardService.findBoardMaster();
        return resultList;
    }

    /**
     * 게시물 이동 처리 (다중)
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/move")
    @ResponseBody
    public Map<String, Object> moveBoardMaster(@RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt( String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx", userIdx);

        int result = boardService.moveBoards(paramMap);
        return ResultStr.setMulti(result);
    }



    /**
     * 게시물 상세
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/{boardId}/{idx}")
    public String boardDetails(@PathVariable("boardId") String boardId, @PathVariable("idx") int idx,HttpServletResponse response, HttpServletRequest request, Model model) {

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("idx",idx);
        paramMap.put("boardId",boardId);

        BoardMaster boardMaster = boardService.findAllByIdxBoardMaster(paramMap);
        Map<String,Object> boardDetail = boardService.findAllByIdx(paramMap);
        List<AttachFile> fileList = boardService.findAllByIdxAttachFile(idx);
        List<Map<String,Object>> commentList = boardService.findAllByIdxBoardComment(paramMap);

        model.addAttribute("boardMaster", boardMaster);
        model.addAttribute("boardDetail", boardDetail);
        model.addAttribute("fileList", fileList);
        model.addAttribute("commentList", commentList);

        return "/board/boardDetail";
    }

    /**
     * 대댓글 보기
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/comment/{idx}")
    @ResponseBody
    public List<Map<String, Object>> moreBoardComment(@PathVariable("idx") int idx, HttpServletResponse response, HttpServletRequest request) {
        return boardService.moreBoardComment(idx);
    }

    /**
     * 게시물 댓글 작성
     * @param comment
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/comment")
    @ResponseBody
    public Map<String, Object> insertBoardComment(@RequestBody BoardComment comment, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
//        int idx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
//        comment.setCreateIdx(idx);
        comment.setCreateIdx(1);

        int result = boardService.insertBoardComment(comment);
        return ResultStr.setMulti(result);
    }

    /**
     * 게시물 댓글 수정
     * @param comment
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/comment")
    @ResponseBody
    public Map<String, Object> updateBoardComment(@RequestBody BoardComment comment, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int idx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        comment.setUpdateIdx(idx);

        int result = boardService.updateBoardComment(comment);
        return ResultStr.setMulti(result);
    }

    /**
     * 게시물 댓글 삭제(유저)
     * @param comment
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/comment/user")
    @ResponseBody
    public Map<String, Object> deleteBoardCommentUser(@RequestBody BoardComment comment, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int idx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        comment.setUpdateIdx(idx);

        int result = boardService.deleteBoardCommentUser(comment);
        return ResultStr.setMulti(result);
    }

    /**
     * 게시물 댓글 삭제(관리자)
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/comment/admin")
    @ResponseBody
    public Map<String, Object> deleteBoardCommentAdmin(@RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int idx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        paramMap.put("updateIdx", idx);

        int result = boardService.deleteBoardCommentAdmin(paramMap);
        return ResultStr.setMulti(result);
    }


    /**
     * 게시물 등록 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/{boardId}/detail")
    public String registerPage(@PathVariable("boardId") String boardId,HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String,Object> boardMaster = boardService.findByBoardIdBoardMaster(boardId);

        model.addAttribute("boardMaster", boardMaster);

        return "/board/boardRegist";
    }

    /**
     * 게시물 등록
     * @param files
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/{boardId}/detail")
    @ResponseBody
    public Map<String, Object> insertBoard(@PathVariable("boardId") String boardId, MultipartFile[] files, MultipartFile thumb,
                                           @RequestPart("param") Board board, HttpServletResponse response, HttpServletRequest request,
                                            Authentication authentication) {
        int result = 0;
        HttpSession session = request.getSession();
        int idx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        board.setCreateIdx(idx);

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardId", boardId);
        BoardMaster boardMaster = boardService.findAllByIdxBoardMaster(paramMap);
        String writeLevel = boardMaster.getWriteLevel();

        // 게시글 작성 권한 비교
        if(compareWriteLevel(authentication, writeLevel) == 0){
            result = 0;
            return ResultStr.setMulti(result);
        }

        // 작성 권한 확인 후 masterIdx 저장
        board.setMasterIdx(boardMaster.getIdx());

        if(thumb != null){
            if(!thumb.getContentType().contains("image")){
                result = 0;
                return ResultStr.setMulti(result);
            }
        }

        result = boardService.insertBoard(files,thumb,board);

        return ResultStr.setMulti(result);
    }

    /**
     * 게시물 작성 권한 비교
     * @param authentication
     * @param writeLevel
     * @return
     */
    private int compareWriteLevel(Authentication authentication, String writeLevel) {
        // ALL - >
        // writeLevel = user, ADMIN
        // authentication.getAuthorities() = [ROLE_USER], [ROLE_ADMIN], [ROLE_MANAGER], [ROLE_SYSTEM]
        // USER - >
        // writeLevel = user, ADMIN
        // authentication.getAuthorities() = [ROLE_USER], [ROLE_ADMIN], [ROLE_MANAGER], [ROLE_SYSTEM]
        // ADMIN - >
        // writeLevel = ADMIN
        // authentication.getAuthorities() = [ROLE_ADMIN], [ROLE_MANAGER], [ROLE_SYSTEM]

        int result = 0;

        String[] userLevel = {"USER","ADMIN","MANAGER","SYSTEM"};
        String[] adminLevel = {"ADMIN","MANAGER","SYSTEM"};

        String authStr = String.valueOf(authentication.getAuthorities());
        String auth = authStr.substring(authStr.lastIndexOf("_") + 1, authStr.length() - 1);

        if(writeLevel.equals("ALL") || writeLevel.equals("USER")){
            if(Arrays.stream(userLevel).anyMatch(auth::equals)) {
                result =1;
            }
        }else {
            if(Arrays.stream(adminLevel).anyMatch(auth::equals)){
                result =1;
            }
        }
        return result;
    }

    /**
     * 게시물 이동 처리(단일)
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/{boardId}/detail/{idx}/move")
    @ResponseBody
    public Map<String, Object> boardMasterList(@PathVariable("boardId") String boardId, @PathVariable("idx") int idx, @RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        paramMap.put("boardId", boardId);
        paramMap.put("idx", idx);
        paramMap.put("userIdx", userIdx);

        int result = boardService.moveOneBoard(paramMap);
        return ResultStr.setMulti(result);
    }

    /**
     * 게시물 수정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/{boardId}/detail/{idx}")
    public String updatePage(@PathVariable("boardId") String boardId, @PathVariable("idx") int idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("idx",idx);
        paramMap.put("boardId",boardId);

        BoardMaster boardMaster = boardService.findAllByIdxBoardMaster(paramMap);
        Map<String,Object> boardDetail = boardService.findAllByIdx(paramMap);
        List<AttachFile> fileList = boardService.findAllByIdxAttachFile(idx);

        model.addAttribute("boardMaster", boardMaster);
        model.addAttribute("boardDetail", boardDetail);
        model.addAttribute("fileList", fileList);

        return "/board/boardUpdate";
    }

    /**
     * 게시물 수정 처리
     * @param files
     * @param board
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/{boardId}/detail/{idx}")
    @ResponseBody
    public Map<String, Object> updateBoard(@PathVariable("idx") String idx, MultipartFile[] files, MultipartFile thumb,@RequestPart("param") Board board, HttpServletResponse response, HttpServletRequest request) {
        int result = 0;
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        board.setUpdateIdx(userIdx);
        board.setIdx(Integer.parseInt(idx));
        if(thumb != null){
            if(!thumb.getContentType().contains("image")){
                result = 0;
                return ResultStr.setMulti(result);
            }
        }

        result = boardService.updateBoard(files,thumb,board);

        return ResultStr.setMulti(result);
    }

    /**
     * 썸네일 삭제
     * @param idx
     * @return
     */
    @DeleteMapping(value = "/thumbnail/{idx}")
    @ResponseBody
    public Map<String,Object> deleteThumbnail(@PathVariable("idx") String idx) {
        int result = 0;

        result = boardService.updateThumbnailNmByIdx(idx);
        fileUtil.deleteThumbnailFile(idx);

        return ResultStr.set(result);
    }

    /**
     * 게시물 삭제 처리(유저)
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/{boardId}/detail/{idx}/user")
    @ResponseBody
    public Map<String,Object> deleteBoardUser(@PathVariable("boardId") String boardId, @PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("idx",idx);
        paramMap.put("userIdx", userIdx);

        int result = boardService.deleteBoardUser(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 게시물 삭제 처리(관리자)
     * @param boardId
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/{boardId}/detail/{idx}/admin")
    @ResponseBody
    public Map<String,Object> deleteOneBoardAdmin(@PathVariable("boardId") String boardId, @PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx").toString()));
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("boardId", boardId);

        paramMap.put("idx",idx);
        paramMap.put("userIdx", userIdx);

        int result = boardService.deleteOneBoardAdmin(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 조회수 up
     * @param idx
     * @param response
     * @param request
     */
    @PostMapping(value = "/hit/{idx}")
    @ResponseBody
    public void addHit(@PathVariable("idx") int idx, HttpServletResponse response, HttpServletRequest request){
        boardService.addHit(request, response,idx);
    }

}
