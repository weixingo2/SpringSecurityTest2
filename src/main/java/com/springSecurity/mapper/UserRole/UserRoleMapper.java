package com.springSecurity.mapper.UserRole;

import com.springSecurity.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserRoleMapper {
    Integer deleteIds(Map<String, Object> map);

    @Select("select ur.* from sys_user_role ur where ur.userId=#{id}")
    List<UserRole> getAll(Integer id);

    void deleteUserIds(List<Integer> listId);

    @Delete("delete ur.* from sys_user_role ur where ur.userId=#{userId}")
    Integer deleteUserId(Integer userId);

    void save(UserRole userRole);

    void delete(Map<String, Object> map);
}
