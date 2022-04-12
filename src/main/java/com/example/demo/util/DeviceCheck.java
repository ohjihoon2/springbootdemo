package com.example.demo.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceCheck {

    private static Integer mobilePageCount;
    private static Integer webPageCount;

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

    /**
     * 모든 페이지 10 통일
     * @return
     */
    public static int getWebPageCount(){
        return webPageCount;
    }
}
