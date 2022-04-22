package com.example.demo.service;

import com.example.demo.vo.MenuTree;

import java.util.List;

public interface CommonService {

    List<MenuTree> findLinkNameLvl1ByUseYn();

    List<MenuTree> findLinkNameLvl2ByUseYn(int lvl);
}
