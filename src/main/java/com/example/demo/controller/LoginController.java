package com.example.demo.controller;

import com.example.demo.form.UserSaveForm;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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

    /**
     * 로그인 페이지
     * @param userVO
     * @return
     */
    @GetMapping(value = "/login")
    public String loginView(UserVO userVO, HttpServletResponse response, HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("prevPage", referrer);

        return "/login/login";
    }

    /**
     * 로그인 성공페이지
     * @param userVO
     * @return
     */
    @GetMapping(value = "/loginSuccess")
    public String loginProc(UserVO userVO, Authentication auth) {

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
        System.out.println("LoginController.verifyMail");

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
     * 인증 코드 메일 보내기
     * @param map
     * @param request
     * @return
     */
    @PostMapping(value = "/sendVerificationMail")
    @ResponseBody
    public int sendVerificationMail(@RequestBody Map<String, Object> map, HttpServletRequest request){
        return loginService.sendVerificationMail(request,map);
    }
    /**
     * 회원가입 요청
     * @param userSaveForm
     * @return
     */
    @PostMapping(value = "/signup")
    public String signupProc(@Validated @ModelAttribute("user") UserSaveForm userSaveForm,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생 errors={}", bindingResult);
            return "login/signup";
        }
        int result = loginService.userSave(userSaveForm);
        redirectAttributes.addAttribute("result", result);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/";

    }

    @GetMapping(value = "/signup")
    public String signupView(UserVO userVO, HttpServletResponse response, HttpServletRequest request) {
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

        UserVO userVO = loginService.findByEmailAndUserNm(paraMap);
        result = userVO.getUserId();

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
     * 아이디 체크
     * @param paraMap
     * @return
     */
    @PostMapping(value = "/checkId")
    @ResponseBody
    public String checkId(@RequestBody HashMap<String, String> paraMap) {
        System.out.println("paraMap" + paraMap.toString());
        UserVO user = loginService.checkUserByUserId(paraMap.get("userId"));
        if (user == null) {
            return "success";
        } else {
            return "fail";
        }
    }

    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

}
