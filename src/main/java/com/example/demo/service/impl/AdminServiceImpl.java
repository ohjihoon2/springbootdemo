package com.example.demo.service.impl;

import com.example.demo.form.UserSaveForm;
import com.example.demo.repository.AdminMapper;
import com.example.demo.repository.LoginMapper;
import com.example.demo.service.AdminService;
import com.example.demo.service.EmailService;
import com.example.demo.service.LoginService;
import com.example.demo.util.RandomString;
import com.example.demo.vo.MenuTree;
import com.example.demo.vo.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public int addMenuTree(MenuTree menuTree) {
        return adminMapper.insertMenuTree(menuTree);
    }

    @Override
    public List<Map<String,Object>> findAllMenuTree() {
        return adminMapper.findAllMenuTree();
    }
}
