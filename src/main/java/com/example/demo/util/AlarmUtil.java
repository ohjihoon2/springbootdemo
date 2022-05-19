package com.example.demo.util;

import com.example.demo.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmUtil {

    private final CommonService commonService;

    public int getAlarmCnt(int userIdx){
        return commonService.countAlarmByUserIdx(userIdx);
    }

}
