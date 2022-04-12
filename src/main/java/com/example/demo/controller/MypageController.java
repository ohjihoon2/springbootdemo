package com.example.demo.controller;

import com.example.demo.service.MypageService;
import com.example.demo.util.ResultStr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Map;

@Controller
public class MypageController {

    @Autowired
    MypageService mypageService;

    /**
     * 회원 탈퇴
     * @param idx
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/user/withdraw/{idx}")
    @ResponseBody
    public Map<String,Object> withdrawUser(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal, HttpServletResponse response, HttpServletRequest request) {
        paramMap.put("idx",idx);
        String[] roleType = {"ROLE_USER"};
        paramMap.put("roleType", roleType);
        int result = mypageService.withdrawUser(paramMap);

        return ResultStr.set(result);
    }
}
