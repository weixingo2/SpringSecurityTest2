package com.springSecurity.service.user;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.UserCountDto;
import com.springSecurity.dto.UserDto;
import com.springSecurity.dto.UserProvincesDto;
import com.springSecurity.entity.User;
import com.springSecurity.from.UserSearchFrom;
import com.springSecurity.vo.UserRoleOrganization;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface UserService {
    User getByUsername(String username);

    String getUserAuthorityInfo(Integer userId);

    User getInfo(Integer id);

    void saveUser(User user,HttpServletRequest request);

    Integer updateUser(User user,HttpServletRequest request);

    void deleteIds(Map<String, Object> map,HttpServletRequest request);

    PageInfo<UserRoleOrganization> getAll1(UserSearchFrom from, Pageable pageable, HttpServletRequest request);


    void insert(String username, String url);

    List<UserDto> getEchart();


    User getUserId(Integer id);

    Integer updateUserPassword(User user);


    List<User> getAll();

    List<UserProvincesDto> getProinces();

    List<UserCountDto> getCount();
}
