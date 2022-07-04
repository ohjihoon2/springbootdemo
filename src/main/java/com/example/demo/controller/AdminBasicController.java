package com.example.demo.controller;

import com.example.demo.service.AdminBasicService;
import com.example.demo.service.CommonService;
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
public class AdminBasicController {

    private final AdminBasicService adminService;
    private final CommonService commonService;

    @GetMapping(value = "/admIndex")
    public String adminPage(Principal principal, Users users, HttpServletResponse response, HttpServletRequest request, Model model) {
        return "/adm/admIndex";
    }

    /**
     * admConfig 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/config")
    public String configDetails(HttpServletResponse response, HttpServletRequest request,Model model) {
//        List<MenuTree> resultList = adminService.findAllMenuTree();

//        model.addAttribute("resultList", resultList);
        return "/adm/config";
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
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));

        for (Map<String, Object> map : paramMapList) {
            map.put("userIdx",userIdx);
        }

        Map<String,Object> resultMap = new HashMap<>();
        int result = adminService.addMenuTree(paramMapList);

        //동적 메뉴정보 갱신
        commonService.refreshSingletonMenuInfo();

        if(result > 0) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

}
