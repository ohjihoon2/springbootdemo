package com.example.demo.controller;

import com.example.demo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("adm")
public class AdminController {

    @GetMapping(value = "/admIndex")
    public String adminPage(UserVO userVO, HttpServletResponse response, HttpServletRequest request) {
        return "/adm/admIndex";
    }
}
