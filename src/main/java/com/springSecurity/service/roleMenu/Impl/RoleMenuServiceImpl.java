package com.springSecurity.service.roleMenu.Impl;

import com.springSecurity.entity.RoleMenu;
import com.springSecurity.mapper.RoleMenu.RoleMenuMapper;
import com.springSecurity.service.roleMenu.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;
    @Override
    public Integer removeRoleMenu(Integer id) {

        return roleMenuMapper.removeRoleMenu(id);

    }

    @Override
    public List<RoleMenu> getAll(Integer id) {

        System.out.println(id);
        List<RoleMenu> list=roleMenuMapper.getAll(id);

        return list;
    }

    @Override
    public Integer deleteIds(Map<String, Object> map) {

        return roleMenuMapper.deleteIds(map);

    }

    @Override
    public void deleteId(Integer roleId) {
        roleMenuMapper.deleteId(roleId);
    }

    @Override
    public Integer insert(RoleMenu roleMenu) {
        return roleMenuMapper.insert(roleMenu);
    }

    @Override
    public Integer getMenu(Integer id) {
        return roleMenuMapper.getMenu(id);
    }

    @Override
    public Integer getNumber(Integer id) {
        return roleMenuMapper.getNumber(id);
    }
}
