package com.example.demo.controller;

import com.example.demo.service.AdminService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.*;
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
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "/admIndex")
    public String adminPage(Principal principal,User user, HttpServletResponse response, HttpServletRequest request, Model model) {
        return "/adm/admIndex";
    }

    /**
     * menuTree 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/menuTree")
    public String menuTreeDetails(HttpServletResponse response, HttpServletRequest request,Model model) {
        List<MenuTree> resultList = adminService.findAllMenuTree();

        String[] split = request.getRequestURI().split("/");
        return "/adm/menuTree";
    }

    /**
     * menuTree 설정 추가
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/menuTree")
    @ResponseBody
    public Map<String,Object> menuTreeSave(@RequestBody List<Map<String,Object>> paramMapList, HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<>();
        int result = adminService.addMenuTree(paramMapList);
        if(result > 0) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    /**
     * board master 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/boardMaster")
    public String boardMasterList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = adminService.findAllBoardMaster(criteria);
        int total = adminService.countBoardMaster(criteria);
        Page pageMaker = new Page(total, 10, criteria);

        model.addAttribute("resultList", resultList);


        model.addAttribute("pageMaker", pageMaker);
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

        int result = adminService.insertBoardMaster(paramMap);

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

        int result = adminService.updateBoardMaster(paramMap);

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

        int result = adminService.deleteBoardMaster(paramMap);

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
        return adminService.findByIdxBoardMaster(idx);
    }

    /**
     * board master boardId 중복 체크
     * @param boardId
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardId")
    @ResponseBody
    public Map<String,Object> existsBoardId(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.existsBoardId(paramMap);

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
    public String contentList(@RequestParam(required = false) Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = adminService.findAllContent(criteria);

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
        return adminService.findByIdxContent(idx);
    }

    @PostMapping(value = "/content")
    @ResponseBody
    public Map<String,Object> saveContent(@RequestBody Map<String,Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = adminService.insertContent(paramMap);

        return ResultStr.set(result);
    }

    @PostMapping(value = "/contentId")
    @ResponseBody
    public Map<String,Object> existsContentId(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.existsContentId(paramMap);

        return ResultStr.set(result);
    }
}
