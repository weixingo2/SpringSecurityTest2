package com.springSecurity.mapper.permission;

import com.springSecurity.entity.Menu;
import com.springSecurity.result.Result;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {
    List<Menu> list(Map<String, Object> map);

    @Select("select m.* from sys_menu m where m.id=#{id}")
    Menu getMenuInfo(Integer id);

    List<Menu> OrderByMenu();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    @Select("select count(*) from sys_menu sm where sm.parentId=#{id}")
    Integer getCount(Integer id);

    @Delete("delete sm.* from sys_menu sm where sm.id=#{id}")
    void deleteMenu(Integer id);


}
