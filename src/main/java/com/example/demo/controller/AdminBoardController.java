package com.example.demo.controller;

import com.example.demo.service.AdminBoardService;
import com.example.demo.util.DeviceCheck;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("adm")
public class AdminBoardController {

    private final AdminBoardService adminService;

    /**
     * board master 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/boardMaster")
    public String boardMasterList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {

        List<Map<String,Object>> resultList = adminService.findAllBoardMaster(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/boardMaster";
    }

    /**
     * board master 설정 추가
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardMaster")
    @ResponseBody
    public Map<String,Object> saveBoardMaster(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = adminService.insertBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board master 설정 수정
     * @param idx
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/boardMaster/{idx}")
    @ResponseBody
    public Map<String,Object> updateBoardMaster(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = adminService.updateBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board master 설정 삭제
     * @param idx
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/boardMaster/{idx}")
    @ResponseBody
    public Map<String,Object> deleteBoardMaster(@PathVariable int idx, Principal principal,HttpServletResponse response, HttpServletRequest request) {

        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("idx",idx);

        int result = adminService.deleteBoardMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * board master 상세 팝업
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/boardMaster/{idx}")
    @ResponseBody
    public Map<String, Object> boardMasterDetails(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request,Model model) {
        return adminService.findByIdxBoardMaster(idx);
    }


    /**
     * board master boardId 중복 체크
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/boardId")
    @ResponseBody
    public Map<String,Object> existsBoardId(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.existsBoardId(paramMap);

        return ResultStr.set(result);
    }

    /**
     * 컨텐츠 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/content")
    public String contentList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = adminService.findAllContent(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/content";
    }

    /**
     * 컨텐츠 상세 팝업
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/content/{idx}")
    @ResponseBody
    public Content contentDetails(@PathVariable int idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        return adminService.findByIdxContent(idx);
    }

    /**
     * 컨첸츠 저장
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/content")
    @ResponseBody
    public Map<String,Object> saveContent(@RequestBody Map<String,Object> paramMap,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = adminService.insertContent(paramMap);

        return ResultStr.set(result);
    }

    /**
     * content 설정 수정
     * @param idx
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/content/{idx}")
    @ResponseBody
    public Map<String,Object> updateContent(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = adminService.updateContent(paramMap);

        return ResultStr.set(result);
    }

    /**
     * content 설정 삭제
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/content/{idx}")
    @ResponseBody
    public Map<String,Object> deleteContent(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);

        int result = adminService.deleteContent(paramMap);

        return ResultStr.set(result);
    }

    /**
     * content contentId 중복 체크
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/contentId")
    @ResponseBody
    public Map<String,Object> existsContentId(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.existsContentId(paramMap);

        return ResultStr.set(result);
    }

    /**
     * Qna 리스트
     * @param pageNum
     * @param searchType
     * @param searchKeyword
     * @param status
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/qna")
    public String qnaList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") String pageNum,
                          @RequestParam(value = "searchType", required = false) String searchType,
                          @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
                          @RequestParam(value = "status", required = false, defaultValue = "wait") String status,
                          HttpServletResponse response, HttpServletRequest request, Model model) {

        Map<String ,Object> mMap = new HashMap<>();
        mMap.put("status",status);

        Criteria criteria = new Criteria();
        criteria.setPageNum(Integer.parseInt(pageNum));
        criteria.setSearchKeyword(searchKeyword);
        criteria.setSearchType(searchType);
        criteria.setParamMap(mMap);

        List<Map<String,Object>> resultList = adminService.findAllQna(criteria);

        int total = adminService.countQna(criteria);

        // 참고 select - option 파라미터
        // criteria - T(QA_SUBJECT) W(userNicknm)
        // 웹 페이징 설정 처리
        int webPageCount = DeviceCheck.getWebPageCount();
        Page pageMaker = new Page(total, webPageCount, criteria);

        model.addAttribute("pageMaker", pageMaker);
        model.addAttribute("resultList", resultList);
        return "/adm/qna";
    }

    /**
     * Qna 상세
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/qna/{idx}")
    @ResponseBody
    public
    Map<String, Object> qnaDetails(@PathVariable int idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        Map<String, Object> resultMap = new HashMap<>();

        List<Qna> resultList = adminService.findByIdxQna(idx);
        QnaConfig config = adminService.findByIdxQnaConfig(idx);

        resultMap.put("resultList", resultList);
        resultMap.put("config", config);

        return resultMap;
    }

    /**
     * Qna 답변
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/qna")
    @ResponseBody
    public Map<String,Object> answerQna(@RequestBody Map<String,Object> paramMap,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = adminService.answerQna(paramMap,request);

        return ResultStr.set(result);
    }

    /**+
     * FAQ Master 설정 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/faqMaster")
    public String faqMasterList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = adminService.findAllFaqMaster(criteria);

        // searchType T : FAQ_NM (FAQ 이름) / I : FAQ_ID (FAQ ID)
        model.addAttribute("resultList", resultList);


        return "/adm/faqMaster";
    }

    /**
     * FAQ MASTER 설정 추가
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/faqMaster")
    @ResponseBody
    public Map<String,Object> saveFaqMaster(@RequestBody Map<String,Object> paramMap,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = adminService.insertFaqMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * FAQ MASTER 설정 수정
     * @param idx
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/faqMaster/{idx}")
    @ResponseBody
    public Map<String,Object> updateFaqMaster(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = adminService.updateFaqMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * FAQ MASTER 설정 삭제
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/faqMaster/{idx}")
    @ResponseBody
    public Map<String,Object> deleteFaqMaster(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);

        int result = adminService.deleteFaqMaster(paramMap);

        return ResultStr.set(result);
    }

    /**
     * FAQ 리스트
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/faq")
    public String faqList(@ModelAttribute Map<String, Object> paramMap, HttpServletResponse response, HttpServletRequest request, Model model) {

        // SELECT 카테고리 LIST
        List<Map<String,Object>> faqList = adminService.findNameFaqMaster();

        List<Map<String,Object>> resultList = adminService.findAllFaq(paramMap);

        model.addAttribute("faqList", faqList);
        model.addAttribute("resultList", resultList);
        return "/adm/faq";
    }

    /**
     * FAQ 추가
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/faq")
    @ResponseBody
    public Map<String,Object> saveFaq(@RequestBody Map<String,Object> paramMap,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = adminService.insertFaq(paramMap);

        return ResultStr.set(result);
    }

    /**
     * FAQ 수정
     * @param idx
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/faq/{idx}")
    @ResponseBody
    public Map<String,Object> updateFaq(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = adminService.updateFaq(paramMap);

        return ResultStr.set(result);
    }

    /**
     * FAQ 삭제
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/faq/{idx}")
    @ResponseBody
    public Map<String,Object> deleteFaq(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);

        int result = adminService.deleteFaq(paramMap);

        return ResultStr.set(result);
    }


    // TODO 2022-04-19
    //  - Qna 첨부파일 /  FILE 테이블 , 로직 수정
}
