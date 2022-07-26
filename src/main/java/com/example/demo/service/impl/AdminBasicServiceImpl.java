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
    public int insertPopup(MultipartFile[] webFiles,MultipartFile[] mobileFiles, Popup popup) {

        if(webFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(webFiles, 0, popup.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            popup.setWebAttachFileIdx(idx);
        }
        if(mobileFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(mobileFiles, 0, popup.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            popup.setMobileAttachFileIdx(idx);
        }

        return adminMapper.insertPopup(popup);
    }

    @Override
    public int updatePopup(MultipartFile[] webFiles, MultipartFile[] mobileFiles,Popup popup) {
        if(webFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(webFiles, 0, popup.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            popup.setWebAttachFileIdx(idx);
        }
        if(mobileFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(mobileFiles, 0, popup.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            popup.setMobileAttachFileIdx(idx);
        }
        return adminMapper.updatePopup(popup);
    }

    @Override
    public int deletePopup(Map<String, Object> paramMap) {
        //TODO 파일 삭제
        return adminMapper.deletePopup(paramMap);
    }

    @Override
    public List<Map<String, Object>> findAllBanner(Criteria criteria) {
        return adminMapper.findAllBanner(criteria);
    }

    @Override
    public Banner findByIdxBanner(int idx) {
        return adminMapper.findByIdxBanner(idx);
    }

    @Override
    public int insertBanner(MultipartFile[] webFiles, MultipartFile[] mobileFiles, Banner banner) {

        if(webFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(webFiles, 0, banner.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            banner.setWebAttachFileIdx(idx);
        }
        if(mobileFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(mobileFiles, 0, banner.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            banner.setMobileAttachFileIdx(idx);
        }

        return adminMapper.insertBanner(banner);
    }

    @Override
    public int updateBanner(MultipartFile[] webFiles, MultipartFile[] mobileFiles, Banner banner) {
        if(webFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(webFiles, 0, banner.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            banner.setWebAttachFileIdx(idx);
        }
        if(mobileFiles != null){

            // 실제 파일 업로드
            List<AttachFile> fileList = fileUtil.uploadFiles(mobileFiles, 0, banner.getCreateIdx());
            // DB에 파일 저장
            int idx = fileUtil.saveFile(fileList);

            //attachFileIdx 저장
            banner.setMobileAttachFileIdx(idx);
        }
        return adminMapper.updateBanner(banner);
    }

    @Override
    public int deleteBanner(Map<String, Object> paramMap) {
        //TODO 파일 삭제
        return adminMapper.deleteBanner(paramMap);
    }

}
