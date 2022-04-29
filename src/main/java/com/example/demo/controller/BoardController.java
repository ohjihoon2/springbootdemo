package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.util.FileUtil;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;


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

//        String boardType = String.valueOf(boardMaster.get("BOARD_TYPE"));
        int webPageCount = DeviceCheck.getPageCount(device);
        
//        int contentCount = DeviceCheck.getContentCount(boardType, device);

//        System.out.println("contentCount = " + contentCount);
//        criteria.setAmount(contentCount);

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
        int result = boardService.deleteBoardAdmin(paramMap);
        return ResultStr.set(result);
    }

//     TODO
//      썸네일 올리기 / 수정 / 삭제

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
        Board board = boardService.findAllByIdx(paramMap);
        List<Map<String,Object>> commentList = boardService.findAllByIdxBoardComment(paramMap);


        model.addAttribute("boardMaster", boardMaster);
        model.addAttribute("board", board);
        model.addAttribute("commentList", commentList);

        return "/board/boardList";
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
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        comment.setCreateIdx(idx);

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
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
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
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        comment.setUpdateIdx(idx);

        int result = boardService.deleteBoardCommentUser(comment);
        return ResultStr.setMulti(result);
    }

    /**
     * 게시물 댓글 삭제(관리자)
     * @param comment
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/comment/admin")
    @ResponseBody
    public Map<String, Object> deleteBoardCommentAdmin(@RequestBody BoardComment comment, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        comment.setUpdateIdx(idx);

        int result = boardService.deleteBoardCommentAdmin(comment);
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
        return "/board/registBoard";
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
    public Map<String, Object> insertBoard(@PathVariable("boardId") String boardId, MultipartFile[] files, MultipartFile thumb,@RequestPart("param") Board board, HttpServletResponse response, HttpServletRequest request) {
        int result = 0;
        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        board.setCreateIdx(idx);

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
     * 게시물 이동 처리(단일)
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/{boardId}/detail/{idx}/move")
    @ResponseBody
    public Map<String, Object> boardMasterList(@RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt((String) session.getAttribute("idx"));
        paramMap.put("userIdx", userIdx);

        int result = boardService.moveBoards(paramMap);
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
        Board board = boardService.findAllByIdx(paramMap);

        model.addAttribute("boardMaster", boardMaster);
        model.addAttribute("board", board);
        return "/board/updateBoard";
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
    public Map<String, Object> updateBoard(@PathVariable("boardId") String boardId, MultipartFile[] files, MultipartFile thumb,@RequestPart("param") Board board, HttpServletResponse response, HttpServletRequest request) {
        int result = 0;
        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        board.setUpdateIdx(idx);

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
        int userIdx = Integer.parseInt((String) session.getAttribute("idx"));
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
    public Map<String,Object> deleteBoardAdmin(@PathVariable("boardId") String boardId, @PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt((String) session.getAttribute("idx"));
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("idx",idx);
        paramMap.put("userIdx", userIdx);

        int result = boardService.deleteBoardAdmin(paramMap);

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
