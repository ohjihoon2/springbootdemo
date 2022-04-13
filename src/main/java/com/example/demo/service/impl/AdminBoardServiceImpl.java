package com.example.demo.service.impl;

import com.example.demo.repository.AdminBoardMapper;
import com.example.demo.service.AdminBoardService;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminBoardServiceImpl implements AdminBoardService {

    private final AdminBoardMapper adminMapper;

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

}
