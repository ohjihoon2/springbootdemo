package com.example.demo.controller;

import com.example.demo.service.FaqService;
import com.example.demo.service.QnaService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.vo.Criteria;
import com.example.demo.vo.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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

        return "/";
    }

    /**
     * qna 전체 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/{idx}")
    public String qnaDetail(@PathVariable("idx") int idx, HttpServletResponse response, HttpServletRequest request, Model model, Device device) {

        Map<String, Object> qnaConfig = qnaService.findByIdxQnaConfig(idx);
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));


        //secret y 일 경우 글쓴이와 답변한 사람 만 들어올 수 있음
        if(qnaConfig.get("secretYn").toString().equals("Y")){
            // TODO secret y 일 경우 글쓴이와 답변한 사람 만 들어올 수 있음
        }

        List<Map<String,Object>> qnaDetail = qnaService.findByIdxQna(idx);


        model.addAttribute("qnaConfig", qnaConfig);
        model.addAttribute("qnaDetail", qnaDetail);

        return "/";
    }
}
