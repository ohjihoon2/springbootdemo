package com.example.demo.service;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.MenuTree;

import java.util.List;

public interface CommonService {

    List<MenuTree> findLinkNameLvlByUseYn();

    AttachFile findAllAttachFile(String saveName);
}
