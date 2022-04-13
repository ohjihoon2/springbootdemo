package com.example.demo.service.impl;

import com.example.demo.repository.AdminBasicMapper;
import com.example.demo.service.AdminBasicService;
import com.example.demo.vo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminBasicServiceImpl implements AdminBasicService {

    private final AdminBasicMapper adminMapper;

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

}
