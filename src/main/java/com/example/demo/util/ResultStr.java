package com.example.demo.util;

import java.util.HashMap;
import java.util.Map;

public class ResultStr {

    /**
     * 결과 값이 단수 일 경우
     * @param result
     * @return
     */
    public final static Map<String,Object> set(int result){
        Map<String, Object> resultMap = new HashMap<>();

        if(result == 1) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    /**
     * 결과 값이 복수 일 경우
     * @param result
     * @return
     */
    public final static Map<String,Object> setMulti(int result){
        Map<String, Object> resultMap = new HashMap<>();

        if(result > 0) {
            resultMap.put("result", "success");
        }else{
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

}
