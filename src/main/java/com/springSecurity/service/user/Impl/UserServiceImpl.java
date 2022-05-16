package com.springSecurity.service.user.Impl;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.UserCountDto;
import com.springSecurity.dto.UserDto;
import com.springSecurity.dto.UserProvincesDto;
import com.springSecurity.entity.Menu;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.entity.Role;
import com.springSecurity.entity.User;
import com.springSecurity.from.UserSearchFrom;
import com.springSecurity.mapper.UserMapper;
import com.springSecurity.service.log.operationLog.OperationLogService;
import com.springSecurity.service.permission.MenuService;
import com.springSecurity.service.permission.PermissionService;
import com.springSecurity.service.role.RoleService;
import com.springSecurity.service.user.UserService;
import com.springSecurity.utils.ObjToMap;
import com.springSecurity.vo.UserRoleOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    MenuService menuService;

    @Autowired
    private OperationLogService operationLogService;




    @Override
    public User getByUsername(String username) {
        return userMapper.getByUsername(username);
    }



    @Override
    public String getUserAuthorityInfo(Integer userId) {

        String authority="";

        //获取角色

        List<Role> roles=roleService.getAllRole(userId);

        if(roles.size()>0){

            String roleCodes=roles.stream().map(r->"ROLE_"+r.getCode()).collect(Collectors.joining(","));

            authority=roleCodes.concat(",");
        }



       List<Integer> menuIds= permissionService.getNavMenuIds(userId);


        Map<String,Object> map=new HashMap<>();

        map.put("menuIds",menuIds);


        if(menuIds.size()>0){

            List<Menu> menus=menuService.list(map);

            String menuPerms=menus.stream().map(m->m.getPerms()).collect(Collectors.joining(","));

            authority=authority.concat(menuPerms);
        }

        return authority;
    }

    @Override
    public User getInfo(Integer id) {
        return userMapper.getInfo(id);
    }

    @Override
    public void saveUser(User user,HttpServletRequest request) {

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("保存用户数据");

        operationLogService.insert(operationLog);


        userMapper.saveUser(user);
    }

    @Override
    public Integer updateUser(User user,HttpServletRequest request) {
        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("编辑用户数据");

        operationLogService.insert(operationLog);

        return userMapper.updateUser(user);
    }

    @Override
    public void deleteIds(Map<String, Object> map,HttpServletRequest request) {

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("删除用户数据");

        operationLogService.insert(operationLog);
        userMapper.deleteId(map);
    }

    @Override
    public PageInfo<UserRoleOrganization> getAll1(UserSearchFrom from, Pageable pageable, HttpServletRequest request) {

        Map<String, Object> objectMap = ObjToMap.getObjectMap(from);


           PageHelper.startPage(from.getPage(), from.getCount());

        List<UserRoleOrganization> userRoleOrganizationList=userMapper.getAll1(objectMap);


        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("获取所有用户数据");

        operationLogService.insert(operationLog);

        return new PageInfo<>(userRoleOrganizationList);

    }

    @Override
    public void insert(String username, String url) {
        userMapper.insert(username,url);
    }


    @Override
    public List<UserDto> getEchart() {
        return userMapper.getEchart();
    }

    @Override
    public User getUserId(Integer id) {
        return userMapper.getUserId(id);
    }

    @Override
    public Integer updateUserPassword(User user) {
        return userMapper.updateUserPassword(user);
    }

    @Override
    public List<User> getAll() {
        return userMapper.getAll();
    }

    @Override
    public List<UserProvincesDto> getProinces() {
        return userMapper.getProinces();
    }

    @Override
    public List<UserCountDto> getCount() {
        return userMapper.getCount();
    }


}
