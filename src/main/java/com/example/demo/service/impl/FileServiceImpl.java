package com.example.demo.service.impl;

import com.example.demo.repository.FileMapper;
import com.example.demo.service.FileService;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.MenuTree;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;

    @Override
    public AttachFile findAllAttachFile(String saveName) {
        return fileMapper.findAllAttachFile(saveName);
    }

    @Override
    public int DeleteAttachFile(String saveName) {
        return 0;
    }

}
