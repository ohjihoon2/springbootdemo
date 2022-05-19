package com.example.demo.repository;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.AttachFileMaster;
import com.example.demo.vo.MenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("CommonMapper")
public interface CommonMapper {

    List<MenuTree>  findLinkNameLvl1ByUseYn();

    List<MenuTree>  findLinkNameLvl2ByUseYn(int lvl);

    int deleteWithdrawUser();

    int countAlarmByUserIdx(int userIdx);
}
