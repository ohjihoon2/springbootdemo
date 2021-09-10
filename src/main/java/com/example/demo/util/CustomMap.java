package com.example.demo.util;

import org.springframework.jdbc.support.JdbcUtils;

import java.util.HashMap;

@SuppressWarnings("serial")
public class CustomMap extends HashMap<String, Object> {

    @Override
    public Object put(String key, Object value) {
        return super.put(JdbcUtils.convertUnderscoreNameToPropertyName(key), value);
    }

}
