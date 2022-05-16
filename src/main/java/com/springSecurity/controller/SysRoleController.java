package com.springSecurity.controller;


import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.Role;
import com.springSecurity.entity.RoleMenu;
import com.springSecurity.from.RoleFrom;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.service.UserRole.UserRoleService;
import com.springSecurity.service.role.RoleService;
import com.springSecurity.service.roleMenu.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/Info")
    @LimitRequest(count=30)

    public Result getInfo(@RequestParam("id") Integer id){

        Role role=roleService.getRoleInfo(id);

        //获取角色相关联的菜单id
        List<RoleMenu> list=roleMenuService.getAll(id);

        List<Integer> menuIds=list.stream().map(p->p.getMenuId()).collect(Collectors.toList());

        role.setMenuIds(menuIds);

        return Result.succ(role);
    }


    @GetMapping("/list")
    @LimitRequest(count=30)

    public Result getList(){

        List<Role> list=roleService.getList();

        return Result.succ(list);
    }

    //加上自定义注解即可
    @PostMapping("/listPage")
    @LimitRequest(count=30)

    public Result getListPage(@RequestBody RoleFrom from,Pageable pageable){

        PageInfo<Role> list=roleService.getListPage1(from,pageable);

        return Result.succ(list);
    }






    @PostMapping("/save")
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result saveRole(@RequestBody Role role, HttpServletRequest request){

        role.setCreated(new Date());
        roleService.saveRole(role,request);
        return Result.succ(role);
    }


    @PostMapping("/update")
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result updateRole(@RequestBody Role role,HttpServletRequest request){

        role.setUpdated(new Date());
        roleService.updateRole(role,request);
        return Result.succ(role);
    }



    @PostMapping("/delete")
    @LimitRequest(count=30)

    @Transactional
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result deleteRoles(@RequestBody Integer[] ids,HttpServletRequest request){

        List<Integer> list= Arrays.asList(ids);

        Map<String,Object> map=new HashMap<>();

        map.put("list",list);

        roleService.deleteRoles(map,request);

        //删除中间表
        userRoleService.deleteIds(map);
        roleMenuService.deleteIds(map);

        return Result.succ("删除成功");


    }



    @PostMapping("/perms/{roleId}")
    @Transactional
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:menu:perm')")
    public Result info(@PathVariable("roleId")Integer roleId,@RequestBody Integer[] menuIds){

        List<RoleMenu> list=new ArrayList<>();

        Arrays.stream(menuIds).forEach(menuId->{

            RoleMenu roleMenu=new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);
            list.add(roleMenu);

        });

        //删除相关的roleMenu
        roleMenuService.deleteId(roleId);
        for(RoleMenu roleMenu:list){
            roleMenuService.insert(roleMenu);
        }
             return Result.succ(menuIds);
    }


    @GetMapping("/test")
    public Result test(@RequestParam("id")Integer id){

        List<RoleMenu> list=roleMenuService.getAll(id);;

        return Result.succ(list);

    }


}
