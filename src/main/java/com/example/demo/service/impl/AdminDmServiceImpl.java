package com.example.demo.service.impl;

import com.example.demo.repository.AdminDmMapper;
import com.example.demo.service.AdminDmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDmServiceImpl implements AdminDmService {

    private final AdminDmMapper adminMapper;
}
