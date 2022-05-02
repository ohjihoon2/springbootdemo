package com.example.demo.controller;

import com.example.demo.service.ContentService;
import com.example.demo.vo.BoardMaster;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("content")
public class ContentController {

    private final ContentService contentService;

    /**
     * 컨텐츠 상세 페이지
     * @param contentId
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping("/{contentId}")
    public String contentDetail(@PathVariable("contentId") String contentId, HttpServletResponse response, HttpServletRequest request, Model model) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("contentId",contentId);

        Map<String,Object> contentDetail = contentService.findAllByContentId(paramMap);

        model.addAttribute("contentDetail", contentDetail);

        return "/content/contentDetail";
    }

}
