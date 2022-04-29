package com.example.demo.util;

import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceCheck {

    private static Integer mobilePageCount;
    private static Integer webPageCount;
    private static Integer contentBasicMobileCount;
    private static Integer contentBasicWebCount;
    private static Integer contentPhotoMobileCount;
    private static Integer contentPhotoWebCount;

// 스프링에서는 정적변수로 선언된 변수에는 injection 할 수 없음
// 1. static을 사용하지 않는 방법
// 2. static을 사용해야할 경우에는 클래스를 @Component 로 등록 후 setter 를 사용하여 injection 후 사용

    @Value("${pageCount.mobile}")
    public void setMobilePageCount(Integer count) {
        mobilePageCount = count;
    }

    @Value("${pageCount.web}")
    public void setWebPageCount(Integer count) {
        webPageCount = count;
    }

    @Value("${contentCount.basic.mobile}")
    public void setContentBasicMobileCount(Integer count) {
        contentBasicMobileCount = count;
    }

    @Value("${contentCount.basic.web}")
    public void setContentBasicWebCount(Integer count) {
        contentBasicWebCount = count;
    }

    @Value("${contentCount.photo.mobile}")
    public void setContentPhotoMobileCount(Integer count) {
        contentPhotoMobileCount = count;
    }

    @Value("${contentCount.photo.web}")
    public void setContentPhotoWebCount(Integer count) {
        contentPhotoWebCount = count;
    }


    public DeviceCheck() {

    }

    public static int getPageCount(Device device) {
        Integer pageCount = 0;
        if (device.isMobile()) {
            pageCount = mobilePageCount;
        } else {
            pageCount = webPageCount;
        }

        return pageCount;
    }

    public static int getContentCount(String boardType, Device device) {
        Integer pageCount = 0;
        if(boardType.equals("basic")){
            if (device.isMobile()) {
                pageCount = contentBasicMobileCount;
            } else {
                pageCount = contentBasicWebCount;
            }
        }else if(boardType.equals("photo")){
            if (device.isMobile()) {
                pageCount = contentPhotoMobileCount;
            } else {
                pageCount = contentPhotoWebCount;
            }
        }

        return pageCount;
    }

    /**
     * 모든 페이지 10 통일
     * @return
     */
    public static int getWebPageCount(){
        return webPageCount;
    }
}
