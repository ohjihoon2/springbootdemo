package com.example.demo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface ContentService {
    Map<String, Object> findAllByContentId(String contentId);

    void contentAddHit(HttpServletRequest request, HttpServletResponse response, int idx);
}
