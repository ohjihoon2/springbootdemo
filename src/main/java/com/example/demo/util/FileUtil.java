package com.example.demo.util;

import com.example.demo.exception.AttachFileException;
import com.example.demo.repository.FileMapper;
import com.example.demo.vo.AttachFile;
import com.example.demo.vo.AttachFileMaster;
import lombok.RequiredArgsConstructor;
import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicMatch;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
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

    /** 썸네일 업로드 경로 */
    @Value("${resources.thumbnail}")
    private String resourcesThumbnail;

    /** 다운로드 경로 */
    @Value("${file.downloadPath}")
    private String fileDownloadPath;

    /** 다운로드 경로 */
    @Value("${file.thumbnailPath}")
    private String fileThumbnailPath;

    private final FileMapper fileMapper;

    /**
     * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
     * @return 랜덤 문자열
     */
    private final String getRandomString(){
        return UUID.randomUUID().toString().replace("-","");
    }

    public List<AttachFile> uploadFiles(MultipartFile[] files, int attachFileIdx, int createIdx){

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

        int cnt = fileMapper.maxAttachIdx(attachFileIdx);

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

    public boolean deleteThumbnailFile(String saveFileName){
        //현재 게시판에 존재하는 파일객체를 만듬
        File file = new File(fileThumbnailPath + saveFileName);

        if(file.exists()) { // 파일이 존재하면
            // 파일 삭제
            file.delete();
        }

        return true;

    }

    /**
     * 파일 저장 (저장 로직)
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

    /**
     * 파일 수정(저장 후 수정 로직)
     * 파일이 수정되는 것이 아닌 기존의 FILE_SN 이후로 추가됨.
     * @param fileList
     * @param attachFileIdx
     * @return
     */
    public int updateFile(List<AttachFile> fileList, int attachFileIdx) {
        int idx = 0;

        if (CollectionUtils.isEmpty(fileList) == false) {

            if(fileMapper.findAttachFileIdxByAttatchFileMaster(attachFileIdx) == 0){

                AttachFileMaster attachFileMaster = new AttachFileMaster();
                fileMapper.insertAttachFileMaster(attachFileMaster);
                idx = attachFileMaster.getIdx();

            }else {
                idx = attachFileIdx;
            }
        }

        for (AttachFile attachFile : fileList) {
            attachFile.setIdx(idx);
        }

        fileMapper.insertAttachFile(fileList);

        return idx;
    }

    public AttachFile uploadThumbnail(MultipartFile thumb, int boardIdx){
        /* 업로드 파일 정보를 담을 비어있는 리스트 */
        AttachFile attachFile= new AttachFile();

        /* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
        File dir = new File(resourcesThumbnail);

        if(dir.exists() == false){
            dir.mkdirs();
        }

        try {
            /* 파일 확장자 */
            final String extension = FilenameUtils.getExtension(thumb.getOriginalFilename());
            /* 서버에 저장할 파일명 (게시판 idx + 확장자) */
            final String saveName = String.valueOf(boardIdx);
//            final String saveName = boardIdx+"."+extension;

            /* 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성 */
            File target = new File(resourcesThumbnail, saveName);
            thumb.transferTo(target);
            makeThumbnail(target.getAbsolutePath(), saveName, extension);

        } catch (IOException e) {
            throw new AttachFileException("[" + thumb.getOriginalFilename() + "] failed to save file...");
        } catch (Exception e) {
            throw new AttachFileException("[" + thumb.getOriginalFilename() + "] failed to save file...");
        }

        return attachFile;
    }

    private void makeThumbnail(String filePath, String fileName, String fileExt) throws Exception {

        // 저장된 원본파일로부터 BufferedImage 객체를 생성합니다.
        BufferedImage srcImg = ImageIO.read(new File(filePath));
        // 썸네일의 너비와 높이 입니다.
        int dw = 285, dh = 285;
        // 원본 이미지의 너비와 높이 입니다.
        int ow = srcImg.getWidth();
        int oh = srcImg.getHeight();

        // 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
        int nw = ow; int nh = (ow * dh) / dw;
        // 계산된 높이가 원본보다 높다면 crop이 안되므로
        // 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
        if(nh > oh) { nw = (oh * dw) / dh; nh = oh; }
        // 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
        BufferedImage cropImg = Scalr.crop(srcImg, (ow-nw)/2, (oh-nh)/2, nw, nh);
        // crop된 이미지로 썸네일을 생성합니다.
        BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
        // 썸네일을 저장합니다. 이미지 이름 앞에 "THUMB_" 를 붙여 표시했습니다.
        String thumbName = fileThumbnailPath + fileName;
        File thumbFile = new File(thumbName);
        ImageIO.write(destImg, fileExt.toUpperCase(), thumbFile);

    }

    public boolean checkImageType(File file){
        Magic magic = new Magic();

        try {
            MagicMatch match = magic.getMagicMatch(file, false);
            return match.getMimeType().contains("image");
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
