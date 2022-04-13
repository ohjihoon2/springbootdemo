package com.example.demo.controller;

import com.example.demo.service.AdminDmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("adm")
public class AdminDmController {

    private final AdminDmService adminService;


}
