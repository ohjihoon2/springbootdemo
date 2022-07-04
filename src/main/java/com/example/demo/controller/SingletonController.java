package com.example.demo.controller;

import com.example.demo.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
@RequiredArgsConstructor
public class SingletonController {

    private final CommonService commonService;

    @PostConstruct
    public void generateSingletonData() throws Exception {
        commonService.generateSingletonData();
    }
}
