package com.example.demo.controller;

import com.example.demo.form.UserSaveForm;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final EmailService emailService;

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


    @PostMapping(value = "/sendVerificationMail")
    @ResponseBody
    public String sendVerificationMail(@RequestBody Map<String, Object> map, HttpServletRequest request){
        String result = "";
        String ranPw = RandomString.randomStr();
        System.out.println("map = " + map);

        String domain = request.getRequestURL().toString().replace(request.getRequestURI(),"");

        String userId = map.get("userId").toString();
        String to = map.get("email").toString();
        String subject = "OO 인증 처리";
        String text = "안녕하세요.<br>" +
                "하단에 링크 클릭 시 인증 처리 됩니다. <br>" +
                "<b><a href=\""+domain+"/verifyMail?id="+userId+"&code="+ranPw+"\">링크</a></b> 입니다.";

        boolean res = emailService.sendMail(to, subject, text);
        System.out.println("res = " + res);
        Map<String,Object> paraMap = new HashMap<>();

        if (res) {
            if(loginService.updateVerificationCode(paraMap)){
                result = "Y";
            }else {
                result = "N";
            }
        } else {
            result = "N";
        }
        return result;
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
    public String forgetPwd(@RequestBody Map<String,Object> paraMap) throws MessagingException {
        String result ="";
        String userNm = (String) paraMap.get("userNm");
        String userId = (String) paraMap.get("userId");

        UserVO userVO = loginService.findByUserNmAndUserId(userNm,userId);

        if(userVO != null) {

            String ranPw = RandomString.randomStrSp();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            userVO.setUserPwd(passwordEncoder.encode(ranPw));
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("userPwd",passwordEncoder.encode(ranPw));
            loginService.updateUserPwd(map);

            String email = userVO.getUserEmail();

            String to = email;
            String subject = "OO 임시 비밀번호 발급";
            String text = "안녕하세요.<br>" +
                    "요청하신 이메일 주소로 임시 비밀번호를 발급하였습니다.<br>" +
                    "비밀번호는 <b>[" + ranPw + "]</b> 입니다. ";
            boolean res = emailService.sendMail(to, subject, text);

            if (res) {
                result = "Y";
            } else {
                result = "N";
            }
        }else{
            result="NONE";
        }

        return result;
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
