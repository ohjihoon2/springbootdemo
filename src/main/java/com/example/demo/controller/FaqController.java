package com.example.demo.controller;

import com.example.demo.service.FaqService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("faq")
public class FaqController {

    private final FaqService faqService;

    /**
     * faq 전체 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("")
    public String faqAllList(Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model, Device device) {
        int pageCount = DeviceCheck.getPageCount(device);
        int total = faqService.countByFaq(criteria);

        Map<String,Object> faqMasterList = faqService.findFaqNmFaqMaster();
        List<Map<String,Object>> faqList = faqService.findAllFaq(criteria);
        Page pageMaker = new Page(total, pageCount, criteria);

        model.addAttribute("pageMaker", pageMaker);

        model.addAttribute("faqList", faqList);
        model.addAttribute("faqMasterList", faqMasterList);

        return "/";
    }

    /**
     * faq id별 리스트
     * @param idx
     * @param criteria
     * @param model
     * @param device
     * @return
     */
    @GetMapping("/{idx}")
    public String faqList(@PathVariable("idx") int idx, Criteria criteria, Model model, Device device) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);
        criteria.setParamMap(paramMap);

        int pageCount = DeviceCheck.getPageCount(device);
        int total = faqService.countByIdxFaq(criteria);
        Map<String,Object> faqMasterList = faqService.findFaqNmFaqMaster();
        Map<String,Object> faqList = faqService.findAllByIdx(criteria);
        Page pageMaker = new Page(total, pageCount, criteria);

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("faqMasterList", faqMasterList);
        model.addAttribute("faqList", faqList);

        return "/";
    }

}
