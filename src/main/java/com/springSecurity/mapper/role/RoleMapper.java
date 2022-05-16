package com.springSecurity.mapper.role;

import com.springSecurity.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper {
    List<Role> getAllRole(Integer userId);

    @Select("select r.* from sys_role r where r.id=${id}")
    Role getRoleInfo(Integer id);

    void saveRole(Role role);

    void updateRole(Role role);

    Integer deleteRoles(Map<String, Object> map);

    @Select("select r.* from sys_role r where r.id=#{id}")
    List<Role> getRoleById(Integer id);



    @Select("select * from sys_role")
    List<Role> getList();

    @Select("select r.* from sys_role r where r.id=#{roleId}")
    Role getRole(Integer roleId);

    List<Role> getListPage1(Map<String, Object> objectMap);
}
