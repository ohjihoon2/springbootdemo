package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("CommonMapper")
public interface CommonMapper {

    List<MenuTree>  findLinkNameLvl1ByUseYn();

    List<MenuTree>  findLinkNameLvl2ByUseYn(String idx);

    int deleteWithdrawUser();

    List<Css> findCssContent();

    Map<String, Object> findSystemConfig();

    int insertVisitor(Visitor visitor);

    List<Popup> findPopup();

    List<Banner> findBanner();
}
