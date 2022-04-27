package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Board;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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
                            , HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardId", boardId);
        criteria.setParamMap(paramMap);

        List<Map<String,Object>> noticeList = null;
        if(criteria.getPageNum() == 1){
            noticeList = boardService.findNoticeByBoardIdBoard(criteria);
        }

        Map<String,Object> boardMaster = boardService.findByBoardIdBoardMaster(boardId);
        List<Map<String,Object>> boardList = boardService.findAllByBoardIdBoard(criteria);


        int total = boardService.countByBoardIdBoard(criteria);
        int webPageCount = DeviceCheck.getWebPageCount();
        Page pageMaker = new Page(total, webPageCount, criteria);

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("boardList", boardList);
        model.addAttribute("boardMaster", boardMaster);

        return "/board/boardList";
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

        model.addAttribute("board", board);
        model.addAttribute("boardMaster", boardMaster);

        return "/board/boardList";
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
    public Map<String, Object> insertBoard(@PathVariable("boardId") String boardId, MultipartFile[] files, @RequestPart("param") Board board, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        board.setCreateIdx(idx);

        int result = boardService.insertBoard(files,board);
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
    public Map<String, Object> updateBoard(@PathVariable("boardId") String boardId, MultipartFile[] files, @RequestPart("param") Board board, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        board.setUpdateIdx(idx);

        int result = boardService.updateBoard(files,board);
        return ResultStr.setMulti(result);
    }


    /**
     * 게시물 삭제 처리
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/{boardId}/detail/{idx}")
    @ResponseBody
    public Map<String,Object> deleteQna(@PathVariable("boardId") String boardId, @PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt((String) session.getAttribute("idx"));
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("idx",idx);
        paramMap.put("userIdx", userIdx);

        int result = boardService.deleteBoard(paramMap);

        return ResultStr.set(result);
    }


    /**
     * 게시물 이동 처리
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/move")
    @ResponseBody
    public Map<String, Object> moveBoardMaster(@RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt((String) session.getAttribute("idx"));
        paramMap.put("userIdx", userIdx);

        int result = boardService.moveBoard(paramMap);
        return ResultStr.setMulti(result);
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
