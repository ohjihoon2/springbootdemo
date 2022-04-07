package com.example.demo.service.impl;

import com.example.demo.repository.AdminMapper;
import com.example.demo.service.AdminService;
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
    @Transactional(rollbackFor = Exception.class)
    public int addMenuTree(List<Map<String,Object>> paramMapList) {
        int i = adminMapper.deleteMenuTree();
        int result = 0;

        System.out.println("delete = " + i);
        try {
            System.out.println("try !!!");
            throw new Exception();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("catch !!!");
        }
        result = adminMapper.insertMenuTree(paramMapList);
        System.out.println("insert = " + result);
        RuntimeException ex = new RuntimeException();
        throw ex;

        //        return adminMapper.insertMenuTree(paramMapList);
    }

    @Override
    public List<MenuTree>  findAllMenuTree() {
        return adminMapper.findAllMenuTree();
    }
}
