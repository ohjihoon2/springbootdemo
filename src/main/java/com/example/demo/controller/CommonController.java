package com.example.demo.controller;

import com.example.demo.service.CommonService;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.SmarteditorVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("cmm")
public class CommonController {
    private CommonService commonService;

    @GetMapping(value = "/menuTree")
    public String useMenuTree(HttpServletResponse response, HttpServletRequest request, Model model) {
        List<MenuTree> resultList = commonService.findLinkNameLvlByUseYn();

        String[] split = request.getRequestURI().split("/");
        model.addAttribute("page",split[2]);
        model.addAttribute("resultList", resultList);
        return "/adm/menuTree";
    }

    @RequestMapping(value="/singleImageUploader")
    public String simpleImageUploader(HttpServletRequest req, SmarteditorVO smarteditorVO) throws UnsupportedEncodingException{
        String callback = smarteditorVO.getCallback();
        String callback_func = smarteditorVO.getCallback_func();
        String file_result = "";
        String result = "";
        MultipartFile multiFile = smarteditorVO.getFiledata();
        try{
            if(multiFile != null && multiFile.getSize() > 0 &&
                    StringUtils.isNotBlank(multiFile.getName())){
                if(multiFile.getContentType().toLowerCase().startsWith("image/")){
                    String oriName = multiFile.getName();
                    String uploadPath = req.getServletContext().getRealPath("/img");
                    String path = uploadPath + "/smarteditor/";
                    File file = new File(path);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    String fileName = UUID.randomUUID().toString();
                    smarteditorVO.getFiledata().transferTo(new File(path + fileName));
                    file_result += "&bNewLine=true&sFileName=" + oriName +
                            "&sFileURL=/img/smarteditor/" + fileName;
                }else{
                    file_result += "&errstr=error";
                }
            }else{
                file_result += "&errstr=error";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        result = "redirect:" + callback +
                "?callback_func=" + URLEncoder.encode(callback_func,"UTF-8") + file_result;
        return result;
    }
}
