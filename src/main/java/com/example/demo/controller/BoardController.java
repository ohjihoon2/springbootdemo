package com.example.demo.controller;

import com.example.demo.service.AdminService;
import com.example.demo.service.BoardService;
import com.example.demo.vo.Board;
import com.example.demo.vo.Search;
import com.example.demo.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("board")
public class BoardController {
    private final BoardService boardService;


    @GetMapping("/boradList/{masterIdx}")
    public String boradList(@PathVariable("masterIdx") String masterIdx, @RequestParam Search search
                            , HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("masterIdx", masterIdx);
        paramMap.put("search", search);

        List<Board> boardList = boardService.findByMasterIdxSearch(paramMap);

        model.addAttribute("boardList", boardList);
        model.addAttribute("search", search);

        return "/board/boradList";
    }

    @GetMapping("/register")
    public String registerPage(HttpServletResponse response, HttpServletRequest request, Model model) {
        return "/board/registBoard";
    }

    /**
     *
     * @param board
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardWithFile")
    @ResponseBody
    public String insertBoard(@RequestBody Board board, HttpServletResponse response, HttpServletRequest request) {
        int result = boardService.insertBoard(board);
        return "/adm/admIndex";
    }

    @PostMapping(value = "/registerWithoutFile")
    @ResponseBody
    public String insertBoard(@RequestPart MultipartFile[] files, @RequestBody Board board, @RequestParam String boardType, @RequestParam int masterIdx, HttpServletResponse response, HttpServletRequest request) {
        int result = boardService.insertBoard(files,board,boardType,masterIdx);
        return "/adm/admIndex";
    }
}
