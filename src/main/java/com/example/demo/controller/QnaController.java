package com.example.demo.controller;

import com.example.demo.service.QnaService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.*;
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
    @GetMapping("/{idx}")
    public String qnaDetail(@PathVariable("idx") int idx, HttpServletResponse response, HttpServletRequest request, Model model, Authentication authentication) {
        Map<String, Object> qnaConfig = qnaService.findByIdxQnaConfig(idx);
        List<List<AttachFile>> fileList = qnaService.findAttachFileIdxByIdxQna(Integer.parseInt(qnaConfig.get("qaIdx").toString()));

        List<Map<String,Object>> qnaDetail = qnaService.findByIdxQna(Integer.parseInt(qnaConfig.get("qaIdx").toString()));

        model.addAttribute("qnaConfig", qnaConfig);
        model.addAttribute("fileList", fileList);
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

        Map<String, Object> configData = SingletonData.getInstance().getConfigData();
        model.addAttribute("qaCategoryList",configData.get("qaCategoryList"));
        model.addAttribute("qaFileLevel",configData.get("qaFileLevel"));
        model.addAttribute("qaEditorLevel",configData.get("qaEditorLevel"));

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
    @PostMapping("/detail")
    @ResponseBody
    public Map<String, Object> insertQna(MultipartFile[] files, @RequestPart("param0") QnaConfig qnaConfig, @RequestPart("param1") Qna qna,
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
     * qna 질문 수정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/detail/{idx}")
    public String updatePage(@PathVariable("idx") int idx,HttpServletResponse response, HttpServletRequest request, Model model) {

        Map<String, Object> configData = SingletonData.getInstance().getConfigData();
        model.addAttribute("qaCategoryList",configData.get("qaCategoryList"));
        model.addAttribute("qaFileLevel",configData.get("qaFileLevel"));
        model.addAttribute("qaEditorLevel",configData.get("qaEditorLevel"));

        Map<String, Object> qnaConfig = qnaService.findByIdxQnaConfig(idx);

        if(qnaConfig.get("qaStatus").equals("A") || qnaConfig.get("qaStatus").equals("RA")){
            Map<String,Object> qnaDetail = qnaService.findByMaxIdxQna(Integer.parseInt(qnaConfig.get("qaIdx").toString()));
            List<AttachFile> fileList = qnaService.findAttachFileIdxByMaxIdxQna(Integer.parseInt(qnaDetail.get("attatchFileIdx").toString()));

            model.addAttribute("qnaDetail", qnaDetail);
            model.addAttribute("fileList", fileList);
        }

        model.addAttribute("qnaConfig", qnaConfig);


        return "/qna/qnaUpdate";
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

        //qaIdx도 추가적으로 받아야함 - 변경할 qna의 idx
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        qna.setUpdateIdx(userIdx);

        result = qnaService.updateQuestion(files,qna);

        return ResultStr.setMulti(result);
    }

}
