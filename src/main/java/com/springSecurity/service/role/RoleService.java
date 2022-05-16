package com.springSecurity.service.role;

import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.Role;
import com.springSecurity.from.RoleFrom;

import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Role> getAllRole(Integer userId);

    Role getRoleInfo(Integer id);

    void saveRole(Role role, HttpServletRequest request);

    void updateRole(Role role,HttpServletRequest request);

    Integer deleteRoles(Map<String, Object> map,HttpServletRequest request);

    List<Role> getRoleById(Integer id);




    List<Role> getList();

    Role getRole(Integer roleId);

    PageInfo<Role> getListPage1(RoleFrom from, Pageable pageable);
}
