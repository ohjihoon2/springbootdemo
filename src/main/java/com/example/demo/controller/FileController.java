package com.example.demo.controller;

import com.example.demo.service.FileService;
import com.example.demo.util.FileUtil;
import com.example.demo.util.ResultStr;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.SmarteditorVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("file")
public class FileController {

    private final FileService fileService;
    private final FileUtil fileUtil;

    @Value("${resources.location}")
    private String resourcesLocation;

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
                    String path = resourcesLocation + "/smarteditor/";
                    File file = new File(path);
                    if(!file.exists()){
                        file.mkdirs();
                    }
                    String fileName = UUID.randomUUID().toString();
                    smarteditorVO.getFiledata().transferTo(new File(path + fileName));
                    file_result += "&bNewLine=true&sFileName=" + oriName +
                            "&sFileURL=/images/smarteditor/" + fileName;
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

    /**
     * 파일 다운로드
     * @param saveName
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping(value="/download/{saveName}")
    public void downloadFile(@PathVariable String saveName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        AttachFile attachFile = fileService.findAllAttachFile(saveName);
        fileUtil.downloadFile(attachFile, response);
    }

    /**
     * 파일 삭제
     * @param saveName
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @DeleteMapping(value="/delete/{saveName}")
    @ResponseBody
    public Map<String, Object> deleteFile(@PathVariable String saveName, HttpServletRequest request, HttpServletResponse response) throws IOException {

        int result = fileService.deleteAttachFile(saveName);

        System.out.println("result = " + result);

        return ResultStr.set(result);
    }

}
