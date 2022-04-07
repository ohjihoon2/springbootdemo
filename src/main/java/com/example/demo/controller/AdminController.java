package com.example.demo.controller;

import com.example.demo.service.AdminService;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String adminPage(User user, HttpServletResponse response, HttpServletRequest request, Model model) {
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
    public Map<String,Object> menuTreeSave(@RequestBody List<Map<String,Object>> paramMapList, HttpServletResponse response, HttpServletRequest request) throws Exception {
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
    public String boardMasterDetails(HttpServletResponse response, HttpServletRequest request,Model model) {
        List<BoardMaster> resultList = adminService.findAllBoradMaster();
 
        model.addAttribute("resultList", resultList);
        return "/adm/boardMaster";
    }

}
