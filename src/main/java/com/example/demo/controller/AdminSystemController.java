package com.example.demo.controller;

import com.example.demo.service.AdminSystemService;
import com.example.demo.service.CommonService;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.Code;
import com.example.demo.vo.CodeGroup;
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
        return "/adm/config";
    }

    /**
     * code group 설정 리스트
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/codeGroup")
    public String systemList(HttpServletResponse response, HttpServletRequest request,Model model) {
        List<CodeGroup> resultList = adminService.findAllCodeGroup();

        model.addAttribute("resultList", resultList);
        return "/adm/system";
    }

    /**
     * code group 상세 팝업
     * @param CodeGroupId
     * @return
     */
    @PostMapping(value = "/codeGroup/{CodeGroupId}")
    @ResponseBody
    public Map<String, Object> systemDetails(@PathVariable String CodeGroupId) {
        Map<String, Object> paramMap = new HashMap<>();
        CodeGroup codeGroup = adminService.findByCodeGroupIdCodeGroup(CodeGroupId);
        List<Code> codeList = adminService.findAllByCodeGroupIdCode(CodeGroupId);
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
//        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("userIdx",1);

        int result = adminService.insertCodeGroup(paramMap);
        //code 정보 갱신
//        commonService.refreshSingletonCodeInfo();

        return ResultStr.setMulti(result);
    }

    /**
     * code group 수정
     * @param CodeGroupId
     * @param paramMap
     * @param principal
     * @param response
     * @param request
     * @return
     */
    @PatchMapping(value = "/codeGroup/{CodeGroupId}")
    @ResponseBody
    public Map<String,Object> updateCssContent(@PathVariable String CodeGroupId, @RequestBody Map<String, Object> paramMap, Principal principal, HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession();
        int userIdx = Integer.parseInt(String.valueOf(session.getAttribute("idx")));
        paramMap.put("CodeGroupId",CodeGroupId);
        paramMap.put("userIdx",userIdx);

        int result = adminService.updateCodeGroup(paramMap);
        //code정보 갱신
//        commonService.refreshSingletonCodeInfo();

        return ResultStr.set(result);
    }

}
