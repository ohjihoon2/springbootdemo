package com.example.demo.service.impl;

import com.example.demo.repository.FileMapper;
import com.example.demo.service.FileService;
import com.example.demo.util.FileUtil;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.MenuTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;
    private final FileUtil fileUtil;

    @Override
    public AttachFile findAllAttachFile(String saveName) {
        return fileMapper.findAllAttachFile(saveName);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
    public int deleteAttachFile(String saveName) {

        int cnt = fileMapper.countFileIdx(saveName);

        int masterIdx = fileMapper.findMasterIdxBySaveName(saveName);
        if(cnt == 1){
            fileMapper.deleteAttachFileMaster(masterIdx);
        }
        fileMapper.deleteAttachFile(saveName);

        if(fileUtil.deleteRealFile(saveName)){
            return 1;
        }
        return 0;
    }

}
