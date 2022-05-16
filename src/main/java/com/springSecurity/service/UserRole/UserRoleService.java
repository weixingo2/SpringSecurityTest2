package com.springSecurity.service.UserRole;

import com.springSecurity.entity.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleService {
    Integer deleteIds(Map<String, Object> map);

    List<UserRole> getAll(Integer id);


    void deleteUserIds(List<Integer> listId);


    Integer deleteUserId(Integer userId);

    void save(UserRole userRole);

    void delete(Map<String, Object> map);
}
