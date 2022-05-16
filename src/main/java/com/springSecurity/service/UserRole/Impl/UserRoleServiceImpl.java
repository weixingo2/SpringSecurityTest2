package com.springSecurity.service.UserRole.Impl;

import com.springSecurity.entity.UserRole;
import com.springSecurity.mapper.UserRole.UserRoleMapper;
import com.springSecurity.service.UserRole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public Integer deleteIds(Map<String, Object> map) {
        return userRoleMapper.deleteIds(map);
    }

    @Override
    public List<UserRole> getAll(Integer id) {
        return userRoleMapper.getAll(id);
    }

    @Override
    public void deleteUserIds(List<Integer> listId) {
        userRoleMapper.deleteUserIds(listId);
    }

    @Override
    public Integer deleteUserId(Integer userId) {
        return userRoleMapper.deleteUserId(userId);
    }

    @Override
    public void save(UserRole userRole) {
        userRoleMapper.save(userRole);
    }

    @Override
    public void delete(Map<String, Object> map) {
        userRoleMapper.delete(map);
    }


}
