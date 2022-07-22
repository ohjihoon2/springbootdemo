package com.example.demo.controller;

import com.example.demo.service.AdminBasicService;
import com.example.demo.service.CommonService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
public class AdminBasicController {

    private final AdminBasicService adminService;
    private final CommonService commonService;

    @GetMapping(value = "/admIndex")
    public String adminPage(Principal principal, Users users, HttpServletResponse response, HttpServletRequest request, Model model) {
        return "/adm/admIndex";
    }

    /**
     * menuTree 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/menuTree")
    public String menuTreeDetails(HttpServletResponse response, HttpServletRequest request,Model model) {
        List<MenuTree> resultList = adminService.findAllMenuTree();

        model.addAttribute("resultList", resultList);
        return "/adm/menuTree";
    }

    /**
     * menuTree 설정 추가
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/menuTree")
    @ResponseBody
    public Map<String,Object> menuTreeSave(@RequestBody List<Map<String,Object>> paramMapList, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));

        for (Map<String, Object> map : paramMapList) {
            map.put("userIdx",userIdx);
        }

        Map<String,Object> resultMap = new HashMap<>();
        int result = adminService.addMenuTree(paramMapList);

        //동적 메뉴정보 갱신
        commonService.refreshSingletonMenuInfo();

        if(result > 0) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    /**
     * css 컨텐츠 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/css")
    public String cssContentList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request,Model model) {
        List<Map<String,Object>> resultList = adminService.findAllCssContent(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/css";
    }

    /**
     * css 컨텐츠 상세 팝업
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/css/{idx}")
    @ResponseBody
    public Css cssContentDetails(@PathVariable int idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        return adminService.findByIdxCssContent(idx);
    }

    /**
     * CSS 컨텐츠 저장
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/css")
    @ResponseBody
    public Map<String,Object> saveCssContent(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",userIdx);

        int result = adminService.insertCssContent(paramMap);
        //동적 css 정보 갱신
        commonService.refreshSingletonCssInfo();

        return ResultStr.set(result);
    }

    /**
     * CSS 컨텐츠 수정
     * @param idx
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/css/{idx}")
    @ResponseBody
    public Map<String,Object> updateCssContent(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal,HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = adminService.updateCssContent(paramMap);
        //동적 css 정보 갱신
        commonService.refreshSingletonCssInfo();

        return ResultStr.set(result);
    }

    /**
     * CSS 컨텐츠 삭제
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/css/{idx}")
    @ResponseBody
    public Map<String,Object> deleteCssContent(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);

        int result = adminService.deleteCssContent(paramMap);
        //동적 css 정보 갱신
        commonService.refreshSingletonCssInfo();

        return ResultStr.set(result);
    }

    /**
     * 팝업 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/popup")
    public String popupList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request,Model model) {
        List<Map<String,Object>> resultList = adminService.findAllPopup(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/css";
    }

    /**
     * 팝업 상세
     * @param idx
     * @param response
     * @param request
     * @param model
     * @return
     */
    @PostMapping(value = "/popup/{idx}")
    @ResponseBody
    public Popup popupDetails(@PathVariable int idx, HttpServletResponse response, HttpServletRequest request, Model model) {
        return adminService.findByIdxPopup(idx);
    }

    /**
     * 팝업 저장
     * @param files
     * @param popup
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/popup")
    @ResponseBody
    public Map<String,Object> savePopup(MultipartFile[] files, @RequestPart("popup") Popup popup, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        popup.setCreateIdx(userIdx);

        int result = adminService.insertPopup(files, popup);

        return ResultStr.set(result);
    }
}
