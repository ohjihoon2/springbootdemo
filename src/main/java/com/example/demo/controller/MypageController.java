package com.example.demo.controller;

import com.example.demo.service.CommonService;
import com.example.demo.service.MypageService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("mypage")
public class MypageController {

    private final MypageService mypageService;
    private final CommonService commonService;

    /**
     * 마이페이지 메인
     * @param request
     * @param model
     * @return
     */
    @GetMapping("")
    public String mypageMain(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String idx = session.getAttribute("idx").toString();
        Users user = mypageService.findUserInfoByIdx(idx);
        model.addAttribute("user", user);
        return "/mypage/mypageMain";
    }

    /**
     * 프로필 업로드
     * @param profile
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public Map<String,Object> updateProfile(MultipartFile profile, HttpServletResponse response, HttpServletRequest request, Model model) {
        int result = 0;
        String idx = request.getSession().getAttribute("idx").toString();
        result = mypageService.updateProfile(profile,idx);
        return ResultStr.set(result);
    }

    /**
     * 프로필 삭제
     * @param request
     * @return
     */
    @DeleteMapping(value = "/profile")
    @ResponseBody
    public Map<String,Object> deleteProfile(@SessionAttribute("idx") int idx,HttpServletRequest request) {
        int result = 0;
        result = mypageService.deleteProfile(idx);
        return ResultStr.set(result);
    }

    /**
     * 비밀번호 변경 팝업
     * @return
     */
    @GetMapping(value = "/password")
    public String popupPassword(@SessionAttribute("idx") int idx, Model model) {
        model.addAttribute("idx", idx);
        return "/mypage/password";
    }

    /**
     * 유저 비밀번호 변경
     * @param paramMap
     * @param request
     * @return
     */
    @PatchMapping(value = "/password")
    @ResponseBody
    public Map<String,Object> updatePassword(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
        String idx = request.getSession().getAttribute("idx").toString();
        paramMap.put("idx",idx);
        int result = commonService.updatePassword(paramMap);
        return ResultStr.set(result);
    }


    /**
     * 닉네임 수정
     * @param paramMap
     * @param request
     * @return
     */
    @PatchMapping(value = "/nicknm")
    @ResponseBody
    public Map<String,Object> updateNicknm(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
        String idx = request.getSession().getAttribute("idx").toString();
        paramMap.put("idx",idx);
        int result = mypageService.updateNicknm(paramMap);
        return ResultStr.set(result);
    }

    /**
     * 닉네임 중복 체크
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/checkNicknm")
    @ResponseBody
    public String checkNicknm(@RequestBody HashMap<String, String> paraMap) {
        int result = commonService.checkUserByUserNicknm(paraMap.get("userNicknm"));

        if (result == 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 이메일 중복 체크
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/checkEmail")
    @ResponseBody
    public String checkEmail(@RequestBody HashMap<String, String> paraMap) {
        int result = commonService.checkUserByUserEmail(paraMap.get("userEmail"));
        if (result == 0) {
            return "success";
        } else {
            return "fail";
        }
    }

    /**
     * 이메일 인증 코드 메일 보내기
     * @param map
     * @param request
     * @return
     */
    @PostMapping(value = "/verificationMail")
    @ResponseBody
    public int verificationMail(@RequestBody Map<String, Object> map, HttpServletRequest request){
        return mypageService.sendVerificationMail(request,map);
    }

    /**
     * 이메일 수정
     * @param paramMap
     * @param request
     * @return
     */
    @PatchMapping(value = "/email")
    @ResponseBody
    public Map<String,Object> updateEmail(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
        String idx = request.getSession().getAttribute("idx").toString();
        paramMap.put("idx",idx);
        int result = mypageService.updateEmail(paramMap);
        return ResultStr.set(result);
    }

    /**
     * 휴대전화 수정
     * @param paramMap
     * @param request
     * @return
     */
    @PatchMapping(value = "/phone")
    @ResponseBody
    public Map<String,Object> updatePhone(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
        String idx = request.getSession().getAttribute("idx").toString();
        paramMap.put("idx",idx);
        int result = mypageService.updatePhone(paramMap);
        return ResultStr.set(result);
    }

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
