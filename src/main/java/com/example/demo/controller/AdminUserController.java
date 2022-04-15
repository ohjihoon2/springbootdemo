package com.example.demo.controller;

import com.example.demo.service.AdminUserService;
import com.example.demo.util.DeviceCheck;
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
public class AdminUserController {

    private final AdminUserService adminService;

    /**
     * 유저 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/user")
    public String userList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = adminService.findAllUser(criteria);
        int total = adminService.countUser(criteria);

        System.out.println("criteria = " + criteria);
        // 참고 select - option 파라미터
        // criteria - i(userId) n(userNm) k(userNicknm)
        // 웹 페이징 설정 처리
        int webPageCount =DeviceCheck.getWebPageCount();
        Page pageMaker = new Page(total, webPageCount, criteria);

        model.addAttribute("resultList", resultList);
        model.addAttribute("pageMaker", pageMaker);
        return "/adm/user";
    }

    /**
     * 유저 상세 팝업
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/user/{idx}")
    @ResponseBody
    public User userDetails(@PathVariable int idx,Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request, Model model) {
        paramMap.put("idx",idx);
        String[] roleType = {"ROLE_USER"};
        paramMap.put("roleType", roleType);

        return adminService.findByIdxUser(paramMap);
    }

    /**
     * 유저 수정
     * @param idx
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/user/{idx}")
    @ResponseBody
    public Map<String,Object> updateUser(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);
        paramMap.put("idx",idx);

        int result = adminService.updateUser(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 회원 강제 탈퇴
     * @param idx
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/user/{idx}")
    @ResponseBody
    public Map<String,Object> forceDeleteUser(@PathVariable int idx, Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        paramMap.put("idx",idx);
        String[] roleType = {"ROLE_USER"};
        paramMap.put("roleType", roleType);
        int result = adminService.forceDeleteUser(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 비밀번호 초기화
     * @param paramMap
     * @return
     */
    @PatchMapping(value = "/resetPassword/{idx}")
    @ResponseBody
    public Map<String,Object> resetPassword(@PathVariable int idx, Map<String, Object> paramMap) {
        paramMap.put("idx",idx);
        int result = adminService.resetPassword(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 관리자 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/admin")
    public String adminList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = adminService.findAllAdmin(criteria);
        int total = adminService.countAdmin(criteria);

        // 참고 select - option 파라미터
        // criteria - i(userId) n(userNm) k(userNicknm)

        // 웹 페이징 설정 처리
        int webPageCount =DeviceCheck.getWebPageCount();
        Page pageMaker = new Page(total, webPageCount, criteria);

        model.addAttribute("resultList", resultList);
        model.addAttribute("pageMaker", pageMaker);
        return "/adm/admin";
    }

    /**
     * 관리자 상세
     * @param idx
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/admin/{idx}")
    @ResponseBody
    public User adminDetails(@PathVariable int idx, Map<String, Object> paramMap , HttpServletResponse response, HttpServletRequest request) {
        String[] roleType = {"ROLE_MANAGER"};
        paramMap.put("roleType", roleType);
        paramMap.put("idx", idx);
        return adminService.findByIdxUser(paramMap);
    }

    /**
     * 관리자 정보 변경
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/admin/{idx}")
    @ResponseBody
    public Map<String,Object> updateAdmin(@PathVariable int idx,@RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {
        paramMap.put("idx",idx);
        int result = adminService.updateAdmin(paramMap);
        return ResultStr.set(result);
    }

    /**
     * 관리자 비밀번호 변경
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/admin/password")
    @ResponseBody
    public Map<String,Object> updatePassword(@RequestBody Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.updatePassword(paramMap);
        return ResultStr.set(result);
    }

    /**
     * 관리자 추가
     * @param paramMap
     * @return
     */
    @PostMapping(value = "/admin")
    @ResponseBody
    public Map<String,Object> insertAdmin(@RequestBody Map<String, Object> paramMap){
        int result = adminService.insertAdmin(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 관리자 삭제
     * @param idx
     * @param paramMap
     * @return
     */
    @DeleteMapping(value = "/admin/{idx}")
    @ResponseBody
    public Map<String,Object> forceDeleteAdmin(@PathVariable int idx, Map<String, Object> paramMap) {
        paramMap.put("idx",idx);
        String[] roleType = {"ROLE_MANAGER"};
        paramMap.put("roleType", roleType);
        int result = adminService.forceDeleteUser(paramMap);

        return ResultStr.set(result);
    }

    /**
     * resetPassword 받기
     * @return
     */
    @PostMapping(value = "resetPassword")
    @ResponseBody
    public String getResetPassword(){
        String resetPwd = adminService.getResetPassword();
        return resetPwd;
    }

    /**
     * 비밀번호 변경 팝업
     * @return
     */
    @GetMapping(value = "/popupPw/{idx}")
    public String popupPassword(@PathVariable int idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        model.addAttribute("idx", idx);
        return "/adm/popupPw";
    }
}
