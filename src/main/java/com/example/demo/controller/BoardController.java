package com.example.demo.controller;

import com.example.demo.service.BoardService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Board;
import com.example.demo.vo.Search;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 게시판 리스트
     * @param masterIdx
     * @param search
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/boardList/{boardId}")
    public String boardList(@PathVariable("boardId") String boardId, @RequestParam Search search
                            , HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("boardId", boardId);
        paramMap.put("search", search);

        List<Board> boardList = boardService.findByMasterIdxSearch(paramMap);

        model.addAttribute("boardList", boardList);
        model.addAttribute("search", search);

        return "/board/boardList";
    }

    /**
     * 게시판 상세
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
     * 게시판 등록
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
     * 게시판 등록(파일 없음)
     * @param board
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/registerWithoutFile")
    @ResponseBody
    public Map<String, Object> insertBoard(@RequestBody Board board, Principal principal, HttpServletResponse response, HttpServletRequest request) {
        String userId = principal.getName();
        board.setCreateId(userId);

        int result = boardService.insertBoard(board);
        return ResultStr.set(result);
    }

    /**
     * 게시판 등록 (파일 있음)
     * @param files
     * @param board
     * @param boardType
     * @param masterIdx
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/registerWithFile")
    @ResponseBody
    public String insertBoard(@RequestPart MultipartFile[] files, @RequestBody Board board, @RequestParam String boardType, @RequestParam int masterIdx, HttpServletResponse response, HttpServletRequest request) {
        int result = boardService.insertBoard(files,board,boardType,masterIdx);
        return "/adm/admIndex";
    }
}
