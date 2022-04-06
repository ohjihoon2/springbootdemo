package com.example.demo.repository;

import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("FileMapper")
public interface FileMapper {

    int insertAttachFile(List<AttachFile> fileList);
}
