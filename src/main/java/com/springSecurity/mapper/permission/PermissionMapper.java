package com.springSecurity.mapper.permission;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Integer> getNavMenuIds(Integer userId);
}
