package com.example.demo.service.impl;

import com.example.demo.repository.AdminBasicMapper;
import com.example.demo.service.AdminBasicService;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminBasicServiceImpl implements AdminBasicService {

    private final AdminBasicMapper adminMapper;
    private final FileUtil fileUtil;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int addMenuTree(List<Map<String,Object>> paramMapList) {
        adminMapper.deleteMenuTree();
        int result = 0;

        if(paramMapList.size() == 0){
            result = 1;
            return result;
        }

        result = adminMapper.insertMenuTree(paramMapList);
        return result;
    }

    @Override
    public List<MenuTree>  findAllMenuTree() {
        return adminMapper.findAllMenuTree();
    }

    @Override
    public List<Map<String,Object>> findAllCssContent(Criteria criteria) {
        return adminMapper.findAllCssContent(criteria);
    }

    @Override
    public Css findByIdxCssContent(int idx) {
        return adminMapper.findByIdxCssContent(idx);
    }

    @Override
    public int insertCssContent(Map<String, Object> paramMap) {
        return adminMapper.insertCssContent(paramMap);
    }

    @Override
    public int updateCssContent(Map<String, Object> paramMap) {
        return adminMapper.updateCssContent(paramMap);
    }

    @Override
    public int deleteCssContent(Map<String, Object> paramMap) {
        return adminMapper.deleteCssContent(paramMap);
    }

    @Override
    public List<Map<String, Object>> findAllPopup(Criteria criteria) {
        return adminMapper.findAllPopup(criteria);
    }

    @Override
    public Popup findByIdxPopup(int idx) {
        return adminMapper.findByIdxPopup(idx);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int insertPopup(MultipartFile[] files, Popup popup) {

        if(files != null){
            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(files, 0, popup.getCreateIdx());

            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            popup.setAttachFileIdx(idx);
        }

        return adminMapper.insertPopup(popup);
    }

}
