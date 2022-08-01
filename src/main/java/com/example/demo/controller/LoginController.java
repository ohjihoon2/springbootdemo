package com.example.demo.controller;

import com.example.demo.service.CommonService;
import com.example.demo.service.LoginService;
import com.example.demo.vo.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final CommonService commonService;

    /**
     * 로그인 페이지
     * @param users
     * @return
     */
    @GetMapping(value = "/login")
    public String loginView(Users users, HttpServletResponse response, HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);

        return "/login/login";
    }

    /**
     * 로그인 성공페이지
     * @param users
     * @return
     */
    @GetMapping(value = "/loginSuccess")
    public String loginProc(Users users, Authentication auth) {

        log.debug("auth.getAuthorities(): {}",auth.getAuthorities());

        return "redirect:/";
    }

    /**
     * 인증 성공
     * @return
     */
    @GetMapping(value = "/verifySuccess")
    public String verifySuccess() {
        return "/login/verifySuccess";
    }

    /**
     * 인증 실패
     * @param msg
     * @param model
     * @return
     */
    @GetMapping(value = "/verifyFailure")
    public String verifyFailure(@RequestParam String msg, Model model) {
        model.addAttribute("msg",msg);
        return "/login/verifyFailure";
    }



    /**
     * 메일 첨부 링크 클릭시 인증 체크
     * @param userId
     * @param code
     * @param redirectAttributes
     * @return
     */
    @GetMapping(value = "/verifyMail")
    public String verifyMail(@RequestParam String userId, @RequestParam String code,RedirectAttributes redirectAttributes){

        Map<String, Object> resultMap = loginService.verifyMail(userId,code);

        if(resultMap.get("result").toString().equals("1")){
            redirectAttributes.addAttribute("msg",resultMap.get("msg"));
            return "redirect:/verifySuccess";
        }else{
//            model.addAttribute("msg",resultMap.get("msg"));
            redirectAttributes.addAttribute("msg",resultMap.get("msg"));
            return "redirect:/verifyFailure";
        }
    }

    /**
     * 인증 코드 메일 다시 보내기
     * @param map
     * @param request
     * @return
     */
    @PostMapping(value = "/reVerificationMail")
    @ResponseBody
    public int reVerificationMail(@RequestBody Map<String, Object> map, HttpServletRequest request){
        return loginService.reSendVerificationMail(request,map);
    }

    /**
     * 인증 코드 다른 메일에 다시 보내기
     * @param map
     * @param request
     * @return
     */
    @PostMapping(value = "/otherVerificationMail")
    @ResponseBody
    public int otherVerificationMail(@RequestBody Map<String, Object> map, HttpServletRequest request){
        return loginService.otherVerificationMail(request,map);
    }

    /**
     * 회원가입 요청
     * @param users
     * @param request
     * @return
     */
    @PostMapping(value = "/signup")
    @ResponseBody
    public Map<String, Object> signupProc(@RequestBody Users users, HttpServletRequest request){
        int result = loginService.userSave(users, request);
        Map<String, Object> resultMap = new HashMap<>();

        if(result == 1) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @GetMapping(value = "/signup")
    public String signupView(Users user, HttpServletResponse response, HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);

        return "/login/signup";
    }

    /**
     * 아이디 찾기
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/forgetId")
    @ResponseBody
    public String forgetId(@RequestBody Map<String,Object> paraMap) {
        String result ="";

        Users users = loginService.findByEmailAndUserNm(paraMap);
        result = users.getUserId();

        return result;
    }

    /**
     * 비밀번호 찾기
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/forgetPwd")
    @ResponseBody
    public int forgetPwd(@RequestBody Map<String,Object> paraMap) {
        String userNm = (String) paraMap.get("userNm");
        String userId = (String) paraMap.get("userId");

        return loginService.forgetPwd(userNm,userId);
    }

    /**
     * 아이디 중복 체크
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/checkId")
    @ResponseBody
    public String checkId(@RequestBody HashMap<String, String> paraMap) {
        Users users = loginService.checkUserByUserId(paraMap.get("userId"));
        if (users == null) {
            return "success";
        } else {
            return "fail";
        }
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
     * 로그아웃
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
