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
    public String adminPage() {
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
        return "/adm/popup";
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
     * @param webFiles
     * @param mobileFiles
     * @param popup
     * @param request
     * @return
     */
    @PostMapping(value = "/popup")
    @ResponseBody
    public Map<String,Object> savePopup(MultipartFile[] webFiles,MultipartFile[] mobileFiles, @RequestPart("popup") Popup popup, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        popup.setCreateIdx(userIdx);

        int result = adminService.insertPopup(webFiles,mobileFiles, popup);

        // 팝업 갱신
        commonService.refreshSingletonPopupInfo();

        return ResultStr.set(result);
    }

    /**
     * 팝업 수정
     * @param idx
     * @param webFiles
     * @param mobileFiles
     * @param popup
     * @param request
     * @return
     */
    @PatchMapping(value = "/popup/{idx}")
    @ResponseBody
    public Map<String,Object> updatePopup(@PathVariable int idx, MultipartFile[] webFiles, MultipartFile[] mobileFiles,@RequestPart("popup") Popup popup,  HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        popup.setIdx(idx);
        popup.setUpdateIdx(userIdx);

        int result = adminService.updatePopup(webFiles,mobileFiles, popup);

        // 팝업 갱신
        commonService.refreshSingletonPopupInfo();

        return ResultStr.set(result);
    }

    /**
     * 팝업 삭제
     * @param idx
     * @return
     */
    @DeleteMapping(value = "/popup/{idx}")
    @ResponseBody
    public Map<String,Object> deletePopup(@PathVariable int idx) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);

        int result = adminService.deletePopup(paramMap);
        // 팝업 갱신
        commonService.refreshSingletonPopupInfo();
        return ResultStr.set(result);
    }

    /**
     * 배너 관리 리스트
     * @param criteria
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/banner")
    public String bannerList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request,Model model) {
        List<Map<String,Object>> resultList = adminService.findAllBanner(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/banner";
    }

    /**
     * 배너 상세
     * @param idx
     * @return
     */
    @PostMapping(value = "/banner/{idx}")
    @ResponseBody
    public Banner bannerDetails(@PathVariable int idx) {
        return adminService.findByIdxBanner(idx);
    }

    /**
     * 배너 저장
     * @param webFiles
     * @param mobileFiles
     * @param banner
     * @param request
     * @return
     */
    @PostMapping(value = "/banner")
    @ResponseBody
    public Map<String,Object> saveBanner(MultipartFile[] webFiles,MultipartFile[] mobileFiles, @RequestPart("banner") Banner banner, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        banner.setCreateIdx(userIdx);

        int result = adminService.insertBanner(webFiles,mobileFiles, banner);

        // 배너 갱신
        commonService.refreshSingletonBannerInfo();

        return ResultStr.set(result);
    }

    /**
     * 배너 수정
     * @param idx
     * @param webFiles
     * @param mobileFiles
     * @param banner
     * @param request
     * @return
     */
    @PatchMapping(value = "/banner/{idx}")
    @ResponseBody
    public Map<String,Object> updateBanner(@PathVariable int idx, MultipartFile[] webFiles, MultipartFile[] mobileFiles,@RequestPart("banner") Banner banner,  HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        banner.setIdx(idx);
        banner.setUpdateIdx(userIdx);

        int result = adminService.updateBanner(webFiles,mobileFiles, banner);

        // 배너 갱신
        commonService.refreshSingletonBannerInfo();

        return ResultStr.set(result);
    }

    /**
     * 배너 삭제
     * @param idx
     * @return
     */
    @DeleteMapping(value = "/banner/{idx}")
    @ResponseBody
    public Map<String,Object> deleteBanner(@PathVariable int idx) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("idx",idx);

        int result = adminService.deleteBanner(paramMap);
        // 배너 갱신
        commonService.refreshSingletonBannerInfo();
        return ResultStr.set(result);
    }
}
