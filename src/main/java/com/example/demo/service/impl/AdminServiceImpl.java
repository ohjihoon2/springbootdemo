package com.example.demo.service.impl;

import com.example.demo.repository.AdminMapper;
import com.example.demo.service.AdminService;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int addMenuTree(List<Map<String,Object>> paramMapList) {
        int i = adminMapper.deleteMenuTree();
        int result = 0;

        if(paramMapList.size() == 0){
            result = 1;
            return result;
        }

        result = adminMapper.insertMenuTree(paramMapList);
//        try {
//            throw new NullPointerException();
//        }catch (Exception e){
//            e.printStackTrace();
//            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//        }
        return result;
    }

    @Override
    public List<MenuTree>  findAllMenuTree() {
        return adminMapper.findAllMenuTree();
    }

    @Override
    public List<Map<String,Object>> findAllBoardMaster(Criteria criteria) {
        return adminMapper.findAllBoardMaster(criteria);
    }

    @Override
    public int insertBoardMaster(Map<String, Object> paramMap) {
        return adminMapper.insertBoardMaster(paramMap);
    }

    @Override
    public int existsBoardId(Map<String,Object> paramMap) {
        return adminMapper.existsBoardId(paramMap);
    }

    @Override
    public int updateBoardMaster(Map<String, Object> paramMap) {
        return adminMapper.updateBoardMaster(paramMap);
    }

    @Override
    public BoardMaster findByIdxBoardMaster(int idx) {
        return adminMapper.findByIdxBoardMaster(idx);
    }

    @Override
    public int deleteBoardMaster(Map<String, Object> paramMap) {
        return adminMapper.deleteBoardMaster(paramMap);
    }

    @Override
    public List<Map<String, Object>> findAllContent(Criteria criteria) {
        return adminMapper.findAllContent(criteria);
    }

    @Override
    public Content findByIdxContent(int idx) {
        return adminMapper.findByIdxContent(idx);
    }

    @Override
    public int insertContent(Map<String, Object> paramMap) {
        return adminMapper.insertContent(paramMap);
    }

    @Override
    public int countBoardMaster(Criteria criteria) {
        return adminMapper.countBoardMaster(criteria);
    }

    @Override
    public int existsContentId(Map<String, Object> paramMap) {
        return adminMapper.existsContentId(paramMap);
    }

    @Override
    public int updateContent(Map<String, Object> paramMap) {
        return adminMapper.updateContent(paramMap);
    }

    @Override
    public int deleteContent(Map<String, Object> paramMap) {
        return adminMapper.deleteContent(paramMap);
    }

    @Override
    public int countContent(Criteria criteria) {
        return adminMapper.countContent(criteria);
    }

    @Override
    public List<Map<String, Object>> findAllUser(Criteria criteria) {
        return adminMapper.findAllUser(criteria);
    }

    @Override
    public int countUser(Criteria criteria) {
        return adminMapper.countUser(criteria);
    }

    @Override
    public User findByIdxUser(Map<String, Object> paramMap) {
        return adminMapper.findByIdxUser(paramMap);
    }

    @Override
    public int updateUser(Map<String, Object> paramMap) {
        return adminMapper.updateUser(paramMap);
    }

    @Override
    public int forceDeleteUser(Map<String, Object> paramMap) {
        return adminMapper.forceDeleteUser(paramMap);
    }

    @Override
    public int resetPassword(Map<String, Object> paramMap) {
        paramMap.put("userPwd",passwordEncoder.encode("rhdxhd12!"));

        return adminMapper.resetPassword(paramMap);
    }

    @Override
    public List<Map<String, Object>> findAllAdmin(Criteria criteria) {
        return adminMapper.findAllAdmin(criteria);
    }

    @Override
    public int countAdmin(Criteria criteria) {
        return adminMapper.countAdmin(criteria);
    }

    @Override
    public int updateAdminSelfInfo(Map<String, Object> paramMap) {
        return adminMapper.updateAdminSelfInfo(paramMap);
    }

    @Override
    public int updatePassword(Map<String, Object> paramMap) {
        int result = 0 ;


        String password = String.valueOf(paramMap.get("password"));
        String newPassword = String.valueOf(paramMap.get("newPassword"));

        paramMap.put("userPwd",passwordEncoder.encode(password));
        paramMap.put("newPwd",passwordEncoder.encode(newPassword));

        if( adminMapper.existsPassword(paramMap) == 1) {
            if(adminMapper.updatePassword(paramMap) == 1){
                result = 1;
            }
        }

        return result;
    }

    @Override
    public int insertAdmin(Map<String, Object> paramMap) {
        String password = String.valueOf(paramMap.get("password"));
        paramMap.put("userPwd",passwordEncoder.encode(password));
        return adminMapper.insertAdmin(paramMap);
    }
}
