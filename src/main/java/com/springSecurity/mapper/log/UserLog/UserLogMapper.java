package com.springSecurity.mapper.log.UserLog;

import com.springSecurity.dto.UserDto;
import com.springSecurity.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserLogMapper {

    List<UserLog> getLogs(Map<String, Object> objectMap);

    void insert(UserLog userLog);

    void deleteIds(Map<String, Object> objectMap);

    List<UserDto> getAll(String created);
}
