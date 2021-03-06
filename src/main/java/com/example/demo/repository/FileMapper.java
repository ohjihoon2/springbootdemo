package com.example.demo.repository;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.AttachFileMaster;
import com.example.demo.vo.MenuTree;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("FileMapper")
public interface FileMapper {

    AttachFile findAllAttachFile(String saveName);

    int insertAttachFile(List<AttachFile> fileList);

    int insertAttachFileMaster(AttachFileMaster attachFileMaster);

    int countFileIdx(String saveName);

    int deleteAttachFileMaster(int masterIdx);

    int deleteBySaveNameAttachFile(String saveName);

    int deleteByIdxAttachFile(int idx);

    int findMasterIdxBySaveName(String saveName);

    int maxAttachIdx(int attachFileIdx);

    List<Map<String,Object>> findSaveNameByAttachFileIdx(int idx);
}
