package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;

public class ResultStr {

    public final static Map<String,Object> set(int result){
        Map<String, Object> resultMap = new HashMap<>();

        if(result == 1) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

}
