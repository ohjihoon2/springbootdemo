package com.example.demo.service.impl;

import com.example.demo.repository.AdminMapper;
import com.example.demo.service.AdminService;
import com.example.demo.vo.Board;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    public void inDirectException() throws Exception {
        throw new Exception("간접 처리 방식 !");
    }

    public void callInDirectException() {
        try {
            System.out.println("여기는 call");
            inDirectException();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int addMenuTree(List<Map<String,Object>> paramMapList) {
        int i = adminMapper.deleteMenuTree();
        int result = 0;
        if (true) {
            throw new RuntimeException();
        }
        result = adminMapper.insertMenuTree(paramMapList);
        return result;
    }

    @Override
    public List<MenuTree>  findAllMenuTree() {
        return adminMapper.findAllMenuTree();
    }

    @Override
    public List<BoardMaster> findAllBoardMaster() {
        return adminMapper.findAllBoardMaster();
    }

    @Override
    public int addBoardMaster(Map<String, Object> paramMap) {
        return adminMapper.insertBoardMaster(paramMap);
    }

    @Override
    public int existsBoardId(String boardId) {
        return adminMapper.existsBoardId(boardId);
    }
}
