package com.springSecurity.controller;

import cn.hutool.core.map.MapUtil;
import com.springSecurity.dto.SysMenuDto;
import com.springSecurity.entity.Menu;
import com.springSecurity.entity.RoleMenu;
import com.springSecurity.entity.User;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.service.permission.MenuService;
import com.springSecurity.service.roleMenu.RoleMenuService;
import com.springSecurity.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {


    @Autowired
    private UserService userSevice;

    @Autowired
    private MenuService menuService;


    @Autowired
    private RoleMenuService roleMenuService;

    @CrossOrigin
     @GetMapping("/nav")
    @LimitRequest(count=30)

    public Result nav(Principal principal){
        User user=userSevice.getByUsername(principal.getName());
        //获取权限信息
        String authorityInfo=userSevice.getUserAuthorityInfo(user.getId());
        String[] authroityInfoArray= StringUtils.tokenizeToStringArray(authorityInfo,",");
        //获取导航栏信息
        List<SysMenuDto> navs=menuService.getCurrentUserNav();
        return Result.succ(MapUtil.builder()
                .put("authoritys",authroityInfoArray)
                .put("nav",navs)
                .map());
    }



    @GetMapping("/info")
    @LimitRequest(count=30)

    public Result getMenuInfo(@RequestParam("id") Integer id){
        return Result.succ(menuService.getMenuInfo(id));
    }

    @GetMapping("list")
    @LimitRequest(count=3)
    public Result getMenuList(){
        List<Menu> list=menuService.tree();
        return Result.succ(list);
    }


    @PostMapping("/save")
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result saveMenu(@Validated @RequestBody Menu menu, HttpServletRequest request){

        menu.setCreated(new Date());
        menuService.saveMenu(menu,request);

       return Result.succ(menu);
    }


    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    @LimitRequest(count=30)

    public Result updateMenu(@RequestBody Menu menu,HttpServletRequest request){

        menu.setUpdated(new Date());
        menuService.updateMenu(menu,request);

        return Result.succ(menu);
    }

    @PostMapping("/delete/{id}")
    @LimitRequest(count=30)

    @Transactional
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result deleteMenu(@PathVariable("id")Integer id,HttpServletRequest request){
        Integer count=menuService.getCount(id);
        if(count>0){
            return Result.fail("请删除子级菜单");
        }

        //寻找中间表数据
        Integer number=roleMenuService.getNumber(id);

        if(number>0){

            //同步把中间表删除掉
            roleMenuService.removeRoleMenu(id);
        }

        menuService.removeMenu(id,request);




        return Result.succ("删除成功");
    }

}
