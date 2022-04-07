package com.example.demo.service.impl;

import com.example.demo.repository.AdminMapper;
import com.example.demo.service.AdminService;
import com.example.demo.vo.BoardMaster;
import com.example.demo.vo.MenuTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor=Exception.class)
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public int addMenuTree(List<Map<String,Object>> paramMapList){
        int i = adminMapper.deleteMenuTree();
        int result = 0;
        adminMapper.insertMenuTree(paramMapList);
        RuntimeException ex = new RuntimeException("runtimeException !!!");
        throw ex;
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
}
