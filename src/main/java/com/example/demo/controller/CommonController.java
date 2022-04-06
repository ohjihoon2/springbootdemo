package com.example.demo.controller;

import com.example.demo.service.CommonService;
import com.example.demo.vo.MenuTree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("cmm")
public class CommonController {
    private CommonService commonService;

    @GetMapping(value = "/menuTree")
    public String menuTreeDetails(HttpServletResponse response, HttpServletRequest request, Model model) {
        List<MenuTree> resultList = commonService.findAllMenuTree();

        String[] split = request.getRequestURI().split("/");
        model.addAttribute("page",split[2]);
        model.addAttribute("resultList", resultList);
        return "/adm/menuTree";
    }

    /**
     * menuTree 추가
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/menuTree")
    public Map<String,Object> menuTreeSave(@ModelAttribute List<Map<String,Object>> paramMapList, HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> resultMap = new HashMap<>();
        int result = commonService.addMenuTree(paramMapList);
        if(result == 1) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

}
