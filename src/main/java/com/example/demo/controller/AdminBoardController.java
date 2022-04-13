package com.example.demo.controller;

import com.example.demo.service.AdminBoardService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.Content;
import com.example.demo.vo.Criteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("adm")
public class AdminBoardController {

    private final AdminBoardService service;

    /**
     * board master 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/boardMaster")
    public String boardMasterList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {

        List<Map<String,Object>> resultList = service.findAllBoardMaster(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/boardMaster";
    }

    /**
     * board master 설정 추가
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardMaster")
    @ResponseBody
    public Map<String,Object> saveBoardMaster(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = service.insertBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board master 설정 수정
     * @param idx
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/boardMaster/{idx}")
    @ResponseBody
    public Map<String,Object> updateBoardMaster(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = service.updateBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board master 설정 삭제
     * @param idx
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/boardMaster/{idx}")
    @ResponseBody
    public Map<String,Object> deleteBoardMaster(@PathVariable int idx, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));

        paramMap.put("userIdx",userIdx);
        paramMap.put("idx",idx);

        int result = service.deleteBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board master 상세 팝업
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/boardMaster/{idx}")
    @ResponseBody
    public BoardMaster boardMasterDetails(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request,Model model) {
        return service.findByIdxBoardMaster(idx);
    }


    /**
     * board master boardId 중복 체크
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardId")
    @ResponseBody
    public Map<String,Object> existsBoardId(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = service.existsBoardId(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 컨텐츠 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/content")
    public String contentList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = service.findAllContent(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/content";
    }

    /**
     * 컨텐츠 상세 팝업
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/content/{idx}")
    @ResponseBody
    public Content contentDetails(@PathVariable int idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        return service.findByIdxContent(idx);
    }

    /**
     * 컨첸츠 저장
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/content")
    @ResponseBody
    public Map<String,Object> saveContent(@RequestBody Map<String,Object> paramMap,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = service.insertContent(paramMap);

        return ResultStr.set(result);
    }

    /**
     * content 설정 수정
     * @param idx
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/content/{idx}")
    @ResponseBody
    public Map<String,Object> updateContent(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = service.updateContent(paramMap);

        return ResultStr.set(result);
    }

    /**
     * content 설정 삭제
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/content/{idx}")
    @ResponseBody
    public Map<String,Object> deleteContent(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);

        int result = service.deleteContent(paramMap);

        return ResultStr.set(result);
    }

    /**
     * content contentId 중복 체크
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/contentId")
    @ResponseBody
    public Map<String,Object> existsContentId(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = service.existsContentId(paramMap);

        return ResultStr.set(result);
    }

}
