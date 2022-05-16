package com.springSecurity.service.permission.Impl;

import cn.hutool.json.JSONUtil;
import com.springSecurity.dto.SysMenuDto;
import com.springSecurity.entity.Menu;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.entity.User;
import com.springSecurity.mapper.permission.MenuMapper;
import com.springSecurity.result.Result;
import com.springSecurity.service.log.operationLog.OperationLogService;
import com.springSecurity.service.permission.MenuService;
import com.springSecurity.service.permission.PermissionService;
import com.springSecurity.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuMapper menuMapper;


    @Autowired
    UserService userService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    PermissionService permissionService;

    @Override
    public List<Menu> list(Map<String, Object> map) {
        return menuMapper.list(map);
    }

    @Override
    public List<SysMenuDto> getCurrentUserNav() {

        String username=(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user=userService.getByUsername(username);

        List<Integer> menuIds=permissionService.getNavMenuIds(user.getId());

        Map<String,Object> map=new HashMap<>();

        map.put("menuIds",menuIds);

        List<Menu> menus=menuMapper.list(map);

        //转树形结构
        List<Menu> menuTree=buildTreeMenu(menus);


        //实体类转DTO
        return convert(menuTree);

    }

    @Override
    public Menu getMenuInfo(Integer id) {
        return menuMapper.getMenuInfo(id);
    }

    @Override
    public List<Menu> tree() {

        List<Menu> list=menuMapper.OrderByMenu();

        return buildTreeMenu(list);
    }

    @Override
    public void saveMenu(Menu menu, HttpServletRequest request) {

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("保存权限数据");
        operationLogService.insert(operationLog);
        menuMapper.saveMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu,HttpServletRequest request) {


        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("编辑权限数据");
        operationLogService.insert(operationLog);
        menuMapper.updateMenu(menu);
    }

    @Override
    public Integer getCount(Integer id) {
        return menuMapper.getCount(id);
    }

    @Override
    public void removeMenu(Integer id,HttpServletRequest request) {


        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("删除权限数据");
        operationLogService.insert(operationLog);
        menuMapper.deleteMenu(id);
    }

    private List<SysMenuDto> convert(List<Menu> menuTree){

        List<SysMenuDto> menuDtos=new ArrayList<>();

        menuTree.forEach(m->{

            SysMenuDto dto=new SysMenuDto();
            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());
            dto.setIcon(m.getIcon());

            if(m.getChildren().size()>0){

                dto.setChildren(convert(m.getChildren()));
            }

            menuDtos.add(dto);

        });

        return menuDtos;

    }


    private List<Menu> buildTreeMenu(List<Menu> menus){

        List<Menu> finalMenus=new ArrayList<>();

        for(Menu menu:menus){

            for(Menu e:menus){

                if(menu.getId()==e.getParentId()){

                    menu.getChildren().add(e);
                }
            }

            if(menu.getParentId()==0){

                finalMenus.add(menu);
            }
        }

        System.out.println(JSONUtil.toJsonStr(finalMenus));

        return finalMenus;
    }


}
