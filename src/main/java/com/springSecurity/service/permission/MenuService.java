package com.springSecurity.service.permission;

import com.springSecurity.dto.SysMenuDto;
import com.springSecurity.entity.Menu;
import com.springSecurity.result.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Menu> list(Map<String, Object> map);

    List<SysMenuDto> getCurrentUserNav();

    Menu getMenuInfo(Integer id);

    List<Menu> tree();

    void saveMenu(Menu menu, HttpServletRequest request);

    void updateMenu(Menu menu,HttpServletRequest request);


    Integer getCount(Integer id);

    void removeMenu(Integer id,HttpServletRequest request);
}
