package com.springSecurity.service.roleMenu;

import com.springSecurity.entity.RoleMenu;

import java.util.List;
import java.util.Map;

public interface RoleMenuService {
    Integer removeRoleMenu(Integer id);

    List<RoleMenu> getAll(Integer id);

    Integer deleteIds(Map<String, Object> map);

    void deleteId(Integer roleId);

    Integer insert(RoleMenu roleMenu);

    Integer getMenu(Integer id);

    Integer getNumber(Integer id);
}
