package com.example.demo.service.impl;

import com.example.demo.repository.CommonMapper;
import com.example.demo.service.CommonService;
import com.example.demo.vo.AttachFile;
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
    public List<MenuTree>  findLinkNameLvlByUseYn() {
        return commonMapper.findLinkNameLvlByUseYn();
    }

    @Override
    public AttachFile findAllAttachFile(String saveName) {
        return commonMapper.findAllAttachFile(saveName);
    }

}
