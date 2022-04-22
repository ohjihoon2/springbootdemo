package com.example.demo.util;

import com.example.demo.exception.AttachFileException;
import com.example.demo.repository.FileMapper;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.AttachFileMaster;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class FileUtil {
    /** 업로드 경로 */
    @Value("${resources.location}")
    private String resourcesLocation;

    /** 다운로드 경로 */
    @Value("${file.downloadPath}")
    private String fileDownloadPath;

    private final FileMapper fileMapper;

    /**
     * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
     * @return 랜덤 문자열
     */
    private final String getRandomString(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public List<AttachFile> uploadFiles(MultipartFile[] files, int createIdx){

        /* 파일이 비어있으면 비어있는 리스트 반환 */
        if(files[0].getSize() <1){
            return Collections.emptyList();
        }

        /* 업로드 파일 정보를 담을 비어있는 리스트 */
        List<AttachFile> fileList= new ArrayList<>();

        /* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
        File dir = new File(resourcesLocation);
        if(dir.exists() == false){
            dir.mkdirs();
        }
        int cnt = 0;
        /* 파일 개수만큼 forEach 실행 */
        for(MultipartFile file : files){
            cnt++;
            try {
                /* 파일 확장자 */
                final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
                /* 서버에 저장할 파일명 (랜덤 문자열 + 확장자) */
                final String saveName = getRandomString();

                /* 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성 */
                File target = new File(resourcesLocation, saveName);
                file.transferTo(target);

                /* 파일 정보 저장 */
                AttachFile attachFile = new AttachFile();
                attachFile.setFileSn(cnt);
                attachFile.setOriginalName(file.getOriginalFilename());
                attachFile.setSaveName(saveName);
                attachFile.setSize(file.getSize());
                attachFile.setExtension(extension);
                attachFile.setCreateIdx(createIdx);
                /* 파일 정보 추가 */
                fileList.add(attachFile);

            } catch (IOException e) {
                throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
            } catch (Exception e) {
                throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
            }
        }

        return fileList;
    }

    public void downloadFile(AttachFile file, HttpServletResponse response) throws IOException {
        String saveFileName = file.getSaveName();
        String originalFileName = file.getOriginalName();

        File downloadFile = new File(fileDownloadPath + saveFileName);

        byte fileByte[] = FileUtils.readFileToByteArray(downloadFile);

        response.setContentType("application/octet-stream");
        response.setContentLength(fileByte.length);

        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName,"UTF-8") +"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(fileByte);
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }

    public boolean deleteRealFile(String saveFileName){
        //현재 게시판에 존재하는 파일객체를 만듬
        File file = new File(resourcesLocation + "\\" + saveFileName);

        if(file.exists()) { // 파일이 존재하면
            file.delete();
            // 파일 삭제
        }

        return true;

    }

    /**
     * 파일 저장
     * @param fileList
     * @return
     */
    public int saveFile(List<AttachFile> fileList) {
        int idx = 0;

        if (CollectionUtils.isEmpty(fileList) == false) {

            AttachFileMaster attachFileMaster = new AttachFileMaster();
            fileMapper.insertAttachFileMaster(attachFileMaster);

            idx = attachFileMaster.getIdx();

            for (AttachFile attachFile : fileList) {
                attachFile.setIdx(idx);
            }

            fileMapper.insertAttachFile(fileList);
        }

        return idx;
    }
}
