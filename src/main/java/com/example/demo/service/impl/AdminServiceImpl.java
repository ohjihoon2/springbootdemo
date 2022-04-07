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
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int addMenuTree(List<Map<String,Object>> paramMapList) throws Exception{
        int i = adminMapper.deleteMenuTree();
        int result = 0;

        System.out.println("delete = " + i);
        result = adminMapper.insertMenuTree(paramMapList);
        System.out.println("insert = " + result);
        Exception ex = new Exception("runtimeException !!!");
        throw ex;

//        return adminMapper.insertMenuTree(paramMapList);
    }

    @Override
    public List<MenuTree>  findAllMenuTree() {
        return adminMapper.findAllMenuTree();
    }

    @Override
    public List<BoardMaster> findAllBoradMaster() {
        return adminMapper.findAllBoradMaster();
    }
}
