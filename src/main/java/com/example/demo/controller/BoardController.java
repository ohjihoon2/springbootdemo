package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Board;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

        int total = boardService.countByBoardIdBoard(criteria);
        List<Map<String,Object>> boardList = boardService.findAllByBoardIdBoard(criteria);

        Map<String,Object> boardMaster = boardService.findByBoardIdBoardMaster(boardId);

        int webPageCount = DeviceCheck.getWebPageCount();
        Page pageMaker = new Page(total, webPageCount, criteria);

        model.addAttribute("pageMaker", pageMaker);
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
    public String boardDetails(@PathVariable("idx") String boardId, @PathVariable("idx") int idx,HttpServletResponse response, HttpServletRequest request, Model model) {
        // TODO 2022-04-25
        //  - boardMaster와 join해서 list가 맞는 애들만 뿌려주게 수정
        //  - 게시판 쓰기, 댓글, 읽기 권한 설정

        Map<String, Object> paramMap = new HashMap<>();

        paramMap.put("idx",idx);
        paramMap.put("boardId",boardId);

        Board board = boardService.findAllByIdx(paramMap);

        model.addAttribute("board", board);

        return "/board/boardList";
    }

    /**
     * 게시물 등록 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String registerPage(HttpServletResponse response, HttpServletRequest request, Model model) {
        return "/board/registBoard";
    }

    /**
     * 게시물 등록
     * @param files
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public Map<String, Object> insertBoard(MultipartFile[] files, @RequestPart("param") Board board, HttpServletResponse response, HttpServletRequest request) {

        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        board.setCreateIdx(idx);

        int result = boardService.insertBoard(files,board);
        return ResultStr.setMulti(result);
    }
}
