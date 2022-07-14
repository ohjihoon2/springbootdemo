package com.example.demo.util;

import com.example.demo.vo.SingletonData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigUtil {

    public String getHomepageName(){
        return SingletonData.getInstance().getConfigData().get("homepageName").toString();
    }
}
