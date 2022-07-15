package com.example.demo.util;

import com.example.demo.vo.SingletonData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigUtil {

    public String getValue(String key){
        return SingletonData.getInstance().getConfigData().get(key).toString();
    }
}
