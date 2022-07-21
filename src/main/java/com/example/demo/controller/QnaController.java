package com.example.demo.controller;

import com.example.demo.service.QnaService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Page;
import com.example.demo.vo.Qna;
import com.example.demo.vo.QnaConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("qna")
public class QnaController {

    private final QnaService qnaService;

    /**
     * qna 전체 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("")
    public String qnaList(Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model, Device device) {
        int pageCount = DeviceCheck.getPageCount(device);
        int total = qnaService.countByQna(criteria);

        List<Map<String,Object>> qnaList = qnaService.findAllQna(criteria);
        Page pageMaker = new Page(total, pageCount, criteria);

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("qnaList", qnaList);

        return "/qna/qnaList";
    }

    /**
     * qna 상세 리스트
     * @param idx
     * @param response
     * @param request
     * @param model
     * @param authentication
     * @return
     */
    @GetMapping("/detail/{idx}")
    public String qnaDetail(@PathVariable("idx") int idx, HttpServletResponse response, HttpServletRequest request, Model model, Authentication authentication) {
        Map<String, Object> qnaConfig = qnaService.findByIdxQnaConfig(idx);
       /* HttpSession session = request.getSession();

        //secret y 일 경우 글쓴이와 관리자 만 들어올 수 있음
        if(qnaConfig.get("secretYn").toString().equals("Y")){
            if(authentication.getPrincipal()==null){
                return "/error/denied";
            }
            String[] adminLevel = {"ADMIN","MANAGER","SYSTEM"};
            String authStr = String.valueOf(authentication.getAuthorities());
            String auth = authStr.substring(authStr.lastIndexOf("_") + 1, authStr.length() - 1);
            boolean isAdmin = Arrays.stream(adminLevel).anyMatch(auth::equals);

            boolean isWriter = false;
            int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
            if(Integer.parseInt(qnaConfig.get("createIdx").toString()) == userIdx){
                isWriter = true;
            }

            if(isWriter == false && isAdmin == false){
                return "/error/denied";
            }
        }*/

        List<Map<String,Object>> qnaDetail = qnaService.findByIdxQna(idx);

        model.addAttribute("qnaConfig", qnaConfig);
        model.addAttribute("qnaDetail", qnaDetail);

        return "/qna/qnaDetail";
    }

    /**
     * qna 질문 등록 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/detail")
    public String registerPage(HttpServletResponse response, HttpServletRequest request, Model model) {

        String qaCategory = qnaService.findAllQaCategory();

        String date[] = qaCategory.split(";");
        model.addAttribute(date);

        return "/qna/qnaRegist";
    }

    /**
     * qna 질문 등록
     * @param files
     * @param qnaConfig
     * @param qna
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/question")
    @ResponseBody
    public Map<String, Object> insertQna(MultipartFile[] files, @RequestPart("param1") QnaConfig qnaConfig, @RequestPart("param2") Qna qna,
                            HttpServletResponse response, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        qnaConfig.setCreateIdx(userIdx);
        qna.setCreateIdx(userIdx);

        int result = qnaService.insertQna(files,qnaConfig,qna);

        return ResultStr.setMulti(result);
    }

    /**
     * qna 추가 질문
     * @param files
     * @param qnaConfig
     * @param qna
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping("/additionalQuestion")
    @ResponseBody
    public Map<String, Object> additionalInsertQna(MultipartFile[] files, @RequestPart("param1") QnaConfig qnaConfig, @RequestPart("param2") Qna qna,
                                         HttpServletResponse response, HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        qnaConfig.setCreateIdx(userIdx);
        qna.setCreateIdx(userIdx);

        int result = qnaService.additionalInsertQna(files,qnaConfig,qna);

        return ResultStr.setMulti(result);
    }

    /**
     * qna 질문 수정
     * @param idx
     * @param response
     * @param request
     * @param model
     * @param authentication
     * @return
     */
    @PatchMapping("/detail/{idx}")
    @ResponseBody
    public Map<String, Object> qnaUpdate(@PathVariable("idx") int idx,MultipartFile[] files, @RequestPart("param") Qna qna, HttpServletResponse response, HttpServletRequest request, Model model, Authentication authentication) {
        int result = 0;

        //qnaIdx도 추가적으로 받아야함 - 변경할 qna의 idx
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        qna.setUpdateIdx(userIdx);

        result = qnaService.updateQuestion(files,qna);

        return ResultStr.setMulti(result);
    }

}
