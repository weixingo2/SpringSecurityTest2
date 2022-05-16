package com.springSecurity.mapper.RoleMenu;

import com.springSecurity.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMenuMapper {


    Integer removeRoleMenu(Integer id);


    List<RoleMenu> getAll(Integer id);

    Integer deleteIds(Map<String, Object> map);

    @Delete("delete rm.* from sys_role_menu rm where rm.roleId=#{roleId}")
    void deleteId(Integer roleId);

    Integer insert(RoleMenu roleMenu);

    @Select("select count(*) from sys_role_menu rm where rm.menuId=#{id}")
    Integer getMenu(Integer id);

    @Select("select count(*) from sys_role_menu rm where rm.menuId=#{id}")
    Integer getNumber(Integer id);
}
