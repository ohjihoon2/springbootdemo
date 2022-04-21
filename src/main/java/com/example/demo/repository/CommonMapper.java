package com.example.demo.repository;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.MenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("CommonMapper")
public interface CommonMapper {

    List<MenuTree>  findLinkNameLvlByUseYn();

    int deleteWithdrawUser();

    AttachFile findAllAttachFile(String saveName);
}
