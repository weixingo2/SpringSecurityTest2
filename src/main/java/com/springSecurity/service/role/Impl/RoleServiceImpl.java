package com.springSecurity.service.role.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.entity.Role;
import com.springSecurity.from.RoleFrom;
import com.springSecurity.mapper.role.RoleMapper;
import com.springSecurity.service.log.operationLog.OperationLogService;
import com.springSecurity.service.role.RoleService;
import com.springSecurity.utils.ObjToMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    OperationLogService operationLogService;
    @Override
    public List<Role> getAllRole(Integer userId) {
        return roleMapper.getAllRole(userId);
    }

    @Override
    public Role getRoleInfo(Integer id) {
        return roleMapper.getRoleInfo(id);
    }

    @Override
    public void saveRole(Role role, HttpServletRequest request) {

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("保存角色数据");

        operationLogService.insert(operationLog);
        roleMapper.saveRole(role);
    }

    @Override
    public void updateRole(Role role,HttpServletRequest request) {
        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("编辑角色数据");
        operationLogService.insert(operationLog);
        roleMapper.updateRole(role);
    }

    @Override
    public Integer deleteRoles(Map<String, Object> map,HttpServletRequest request) {

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("删除角色数据");
        operationLogService.insert(operationLog);
        return roleMapper.deleteRoles(map);
    }

    @Override
    public List<Role> getRoleById(Integer id) {
        return roleMapper.getRoleById(id);
    }



    @Override
    public List<Role> getList() {
        return roleMapper.getList();
    }

    @Override
    public Role getRole(Integer roleId) {
        return roleMapper.getRole(roleId);
    }

    @Override
    public PageInfo<Role> getListPage1(RoleFrom from, Pageable pageable) {

        Map<String,Object> objectMap= ObjToMap.getObjectMap(from);

        PageHelper.startPage(from.getPage(), from.getCount());

        List<Role> roles=roleMapper.getListPage1(objectMap);

        return new PageInfo<>(roles);
    }
}
