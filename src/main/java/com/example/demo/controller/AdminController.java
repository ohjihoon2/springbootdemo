package com.example.demo.controller;

import com.example.demo.service.AdminService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        System.out.println("principal = " + principal);
        String name = principal.getName();
        System.out.println("name = " + name);
        String[] split = request.getRequestURI().split("/");
        model.addAttribute("page",split[2]);
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
        model.addAttribute("page",split[2]);
        model.addAttribute("resultList", resultList);
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
     * board 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/boardMaster")
    public String boardMasterList(HttpServletResponse response, HttpServletRequest request,Model model) {
        List<Map<String,Object>> resultList = adminService.findAllBoardMaster();
 
        model.addAttribute("resultList", resultList);
        return "/adm/boardMaster";
    }

    /**
     * board 설정 추가
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardMaster")
    @ResponseBody
    public Map<String,Object> saveBoardMaster(@RequestBody Map<String,Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<>();
        String userId = principal.getName();
        System.out.println("userId = " + userId);
        paramMap.put("userId",userId);

        int result = adminService.addBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board 설정 수정
     * @param idx
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PutMapping(value = "/boardMaster/{idx}")
    @ResponseBody
    public Map<String,Object> updateBoardMaster(@PathVariable int idx, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        String userId = principal.getName();
        System.out.println("userId = " + userId);
        paramMap.put("userId",userId);

        int result = adminService.updateBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board 설정 삭제
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
        String userId = principal.getName();

        paramMap.put("userId",userId);
        paramMap.put("idx",idx);

        int result = adminService.deleteBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 상세페이지
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/boardMaster/{idx}")
    public String boardMasterDetails(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request,Model model) {
        BoardMaster resultMap = adminService.findByIdxBoardMaster(idx);

        model.addAttribute("resultMap", resultMap);
        return "/adm/boardMaster";
    }

    /**
     * boardId 중복 체크
     * @param boardId
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardId")
    @ResponseBody
    public Map<String,Object> boardMasterSave(@RequestBody String boardId, HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<>();

        int result = adminService.existsBoardId(boardId);

        return ResultStr.set(result);
    }
}
