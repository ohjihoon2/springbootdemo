package com.example.demo.service.impl;

import com.example.demo.repository.CommonMapper;
import com.example.demo.service.CommonService;
import com.example.demo.vo.MenuTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final CommonMapper commonMapper;

    @Override
    public int addMenuTree(List<Map<String,Object>> paramMapList) {
        for (Map<String, Object> map : paramMapList) {

        }
        return commonMapper.insertMenuTree(paramMapList);
    }

    @Override
    public List<MenuTree>  findAllMenuTree() {
        return commonMapper.findAllMenuTree();
    }
}
