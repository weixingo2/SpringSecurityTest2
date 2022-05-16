package com.springSecurity.service.log.userLog;


import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.UserDto;
import com.springSecurity.entity.UserLog;
import com.springSecurity.from.SearchFrom;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserLogService {
    PageInfo<UserLog> getLogs(SearchFrom from, Pageable pageable);

    void insert(UserLog userLog);

    void deleteIds(List<Integer> ids, HttpServletRequest request);

    List<UserDto> getAll(String created);
}
