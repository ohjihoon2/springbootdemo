package com.example.demo.controller;

import com.example.demo.service.AdminService;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("adm")
public class AdminController {

    private final AdminService adminService;

    @GetMapping(value = "/admIndex")
    public String adminPage(User user, HttpServletResponse response, HttpServletRequest request) {
        return "/adm/admIndex";
    }

    @GetMapping(value = "/menuTree")
    public String menuTreeDetails(HttpServletResponse response, HttpServletRequest request) {

        return "/adm/menuTree";
    }

    /**
     * menuTree 추가
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/menuTree")
    public String menuTreeSave(@ModelAttribute MenuTree menuTree, HttpServletResponse response, HttpServletRequest request) {
        int result = adminService.addMenuTree(menuTree);


        return "redirect:/menuTree";
    }

}
