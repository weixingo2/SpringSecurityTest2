package com.springSecurity.service.permission.Impl;

import com.springSecurity.mapper.permission.PermissionMapper;
import com.springSecurity.service.permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Integer> getNavMenuIds(Integer userId) {
        return permissionMapper.getNavMenuIds(userId);
    }
}
