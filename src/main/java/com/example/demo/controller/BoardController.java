package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Board;
import com.example.demo.vo.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
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
     * @param masterIdx
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/boardList/{boardId}")
    public String boardList(@PathVariable("boardId") String boardId, @RequestParam Criteria criteria
                            , HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardId", boardId);
        paramMap.put("sriteria", criteria);

        List<Board> boardList = boardService.findByMasterIdxSearch(paramMap);

        model.addAttribute("boardList", boardList);
        model.addAttribute("sriteria", criteria);

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
    @GetMapping("/board/{idx}")
    public String boardDetails(@PathVariable("idx") String idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String, Object> paramMap = new HashMap<>();

        Board board = boardService.findAllByIdx(idx);

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
     * 게시물 등록(파일 없음)
     * @param board
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/registerWithoutFile")
    @ResponseBody
    public Map<String, Object> insertBoard(@RequestBody Map<String, Object> paramMap, Principal principal, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        paramMap.put("createIdx",idx);

        int result = boardService.insertBoard(paramMap);
        return ResultStr.set(result);
    }

    /**
     * 게시물 등록 (파일 있음)
     * @param files
     * @param boardType
     * @param masterIdx
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/registerWithFile")
    @ResponseBody
    // TODO 2022-04-19
    //  - RequestPart 뒤에 VO 객체가 들어가야함 - 객체가 있어야 SET이 된다. 뒤에 들어갈 객체 내용 생각해보고 다시 작성하기
    public Map<String, Object> insertBoard(@RequestPart MultipartFile[] files, @RequestPart Map<String, Object> paramMap, @RequestParam String boardType, @RequestParam int masterIdx, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int idx = Integer.parseInt((String) session.getAttribute("idx"));
        paramMap.put("idx", idx);
//        board.setCreateIdx(idx);

        int result = boardService.insertBoard(files,paramMap);
        return ResultStr.set(result);
    }
}
