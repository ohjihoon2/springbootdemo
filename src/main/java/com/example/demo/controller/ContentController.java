package com.example.demo.controller;

import com.example.demo.service.ContentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        Map<String,Object> contentDetail = contentService.findAllByContentId(contentId);
        System.out.println("contentDetail = " + contentDetail);
        model.addAttribute("contentDetail", contentDetail);

        return "/content/contentDetail";
    }

    /**
     * 조회수 up
     * @param idx
     * @param response
     * @param request
     */
    @PostMapping(value = "/hit/{idx}")
    @ResponseBody
    public void contentAddHit(@PathVariable("idx") int idx, HttpServletResponse response, HttpServletRequest request){
        contentService.contentAddHit(request, response,idx);
    }

}
