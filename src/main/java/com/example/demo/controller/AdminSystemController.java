package com.example.demo.controller;

import com.example.demo.service.AdminSystemService;
import com.example.demo.service.CommonService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
import com.example.demo.vo.Criteria;
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
public class AdminSystemController {

    private final AdminSystemService adminService;
    private final CommonService commonService;

    /**
     * admConfig 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/config")
    public String configDetails(HttpServletResponse response, HttpServletRequest request,Model model) {
//        List<MenuTree> resultList = adminService.findAllMenuTree();

//        model.addAttribute("resultList", resultList);
        return "/adm/system/config";
    }

    /**
     * code group 설정 리스트
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/codeGroup")
    public String systemList(@ModelAttribute Criteria criteria, HttpServletResponse response, HttpServletRequest request, Model model) {
        List<Map<String,Object>> resultList = adminService.findAllCodeGroup(criteria);

        model.addAttribute("resultList", resultList);
        return "/adm/system/codeGroup";
    }

    /**
     * code group 상세 팝업
     * @param idx
     * @return
     */
    @PostMapping(value = "/codeGroup/{idx}")
    @ResponseBody
    public Map<String, Object> systemDetails(@PathVariable int idx) {
        Map<String, Object> paramMap = new HashMap<>();
        CodeGroup codeGroup = adminService.findByIdxCodeGroup(idx);
        List<Code> codeList = adminService.findAllByIdxCode(idx);
        paramMap.put("codeGroup", codeGroup);
        paramMap.put("codeList", codeList);

        return paramMap;
    }

    /**
     * code group 저장
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/codeGroup")
    @ResponseBody
    public Map<String,Object> saveCodeGroup(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx", userIdx);

        int result = adminService.insertCodeGroup(paramMap);
        //code 정보 갱신
        commonService.refreshSingletonCodeInfo();

        return ResultStr.setMulti(result);
    }

    /**
     * code group 수정
     * @param idx
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/codeGroup/{idx}")
    @ResponseBody
    public Map<String,Object> updateCssContent(@PathVariable int idx, @RequestBody Map<String, Object> paramMap, Principal principal, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("idx",idx);
        paramMap.put("userIdx",userIdx);

        int result = adminService.updateCodeGroup(paramMap);
        //code정보 갱신
//        commonService.refreshSingletonCodeInfo();

        return ResultStr.set(result);
    }

    /**
     * codeGroupId 중복 체크
     * @param paramMap
     * @param response
     * @param request
     * @return
     */
    @PostMapping(value = "/codeGroupId")
    @ResponseBody
    public Map<String,Object> existsCodeGroupId(@RequestBody Map<String,Object> paramMap, HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.existsCodeGroupId(paramMap);

        return ResultStr.set(result);
    }
}

