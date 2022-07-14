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
     * SystemConfig 설정 페이지
     * @param response
     * @param request
     * @param model
     * @return
     */
    @GetMapping(value = "/config")
    public String configDetails(HttpServletResponse response, HttpServletRequest request,Model model) {
        Map<String, Object> resultMap = adminService.findSystemConfig();
        model.addAttribute("resultMap", resultMap);
        return "/adm/system/config";
    }

    /**
     * SystemConfig 수정
     * @param paramMap
     * @param request
     * @return
     */
    @PatchMapping(value = "/config")
    @ResponseBody
    public Map<String,Object> updateConfig(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) {
        int result = adminService.updateSystemConfig(paramMap);

        return ResultStr.set(result);
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
        List<Code> codeList = adminService.findAllByCodeGroupIdCode(codeGroup.getCodeGroupId());
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

        return ResultStr.set(result);
    }

    /**
     * code group 삭제
     * @param codeGroupId
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/codeGroup/{codeGroupId}")
    @ResponseBody
    public Map<String,Object> deleteCodeGroup(@PathVariable String codeGroupId,HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.deleteCodeGroup(codeGroupId);

        return ResultStr.set(result);
    }

    /**
     * code 삭제
     * @param idx
     * @param response
     * @param request
     * @return
     */
    @DeleteMapping(value = "/code/{idx}")
    @ResponseBody
    public Map<String,Object> deleteCode(@PathVariable int idx,HttpServletResponse response, HttpServletRequest request) {

        int result = adminService.deleteCode(idx);

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

