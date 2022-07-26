package com.example.demo.repository;

import com.example.demo.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("AdminBasicMapper")
public interface AdminBasicMapper {

    int insertMenuTree(List<Map<String,Object>> paramMapList);

    List<MenuTree>  findAllMenuTree();

    int deleteMenuTree();

    List<Map<String,Object>> findAllCssContent(Criteria criteria);

    Css findByIdxCssContent(int idx);

    int insertCssContent(Map<String, Object> paramMap);

    int updateCssContent(Map<String, Object> paramMap);

    int deleteCssContent(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllPopup(Criteria criteria);

    Popup findByIdxPopup(int idx);

    int insertPopup(Popup popup);

    int updatePopup(Popup popup);

    int deletePopup(Map<String, Object> paramMap);

    List<Map<String, Object>> findAllBanner(Criteria criteria);

    Banner findByIdxBanner(int idx);

    int insertBanner(Banner banner);

    int updateBanner(Banner banner);

    int deleteBanner(Map<String, Object> paramMap);
}
