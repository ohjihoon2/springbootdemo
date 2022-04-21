package com.example.demo.util;

import com.example.demo.exception.AttachFileException;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.Board;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Component
public class FileUtils {
    /** 업로드 경로 */
    @Value("${resources.location}")
    private String resourcesLocation;

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
/*

    public ResponseEntity<Resource> downloadFile(String filename, int seq) throws IOException {
        //경로 설정
//        String fuploadPath = req.getServletContext().getRealPath("/upload");

        //파일정보 설정
        AttachFile attachFile = pdsService.getOnePds(seq);
        String fileName = AttachFile.getFilename();
        String origin_fileName = AttachFile.getOrigin_filename();
        File file = new File(resourcesLocation + "/" + fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Content-Disposition", "attatchment; filename=\"" +
                new String(origin_fileName.getBytes("UTF-8"), "ISO-8859-1") +
                "\"");

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }
*/


/*
    @GetMapping("/attached/{itemId}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable Long fileId) throws MalformedURLException {
        DownloadFile file = fileService.findById(fileId);
        String storedFileName = file.getAttachedFile().getStoredFileName();
        String uploadFileName = file.getAttachedFile().getUploadFileName();
        UrlResource resource = new UrlResource("file:" + resourcesLocation + storedFileName);
        log.info("uploadFileName = {}", uploadFileName);
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDispostion = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok() .header(HttpHeaders.CONTENT_DISPOSITION, contentDispostion) .body(resource);
    }
*/

}
