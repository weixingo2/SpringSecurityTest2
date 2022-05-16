package com.springSecurity.mapper;

import com.springSecurity.dto.UserCountDto;
import com.springSecurity.dto.UserDto;
import com.springSecurity.dto.UserProvincesDto;
import com.springSecurity.entity.User;
import com.springSecurity.from.UserSearchFrom;
import com.springSecurity.vo.UserRoleOrganization;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select("select u.* from sys_user u where u.username=#{username}")
    User getByUsername(String username);

    @Select("select u.* from sys_user u where u.id=#{id}")
    User getInfo(Integer id);

    @Insert("insert into sys_user(username,password,email,city,created,statu,organizationId,provinces,LatLng)values(#{username},#{password},#{email},#{city},#{created},#{statu},#{organizationId},#{provinces},#{LatLng})")
    void saveUser(User user);

    @Update("update sys_user u set u.username=#{username},u.email=#{email},u.statu=#{statu},u.updated=#{updated},u.organizationId=#{organizationId} where u.id=#{id}")
    Integer updateUser(User user);

    void deleteId(Map<String, Object> map);


    List<UserRoleOrganization> getAll1(Map<String, Object> objectMap);

    @Update("update sys_user su set su.avatar=#{url} where su.username=#{username}")
    void insert(String username, String url);

    List<UserDto> getEchart();

    @Select("select u.* from sys_user u where u.id=#{id}")
    User getUserId(Integer id);

    @Update("update sys_user su set su.password=#{password},su.updated=#{updated} where su.id=#{id}")
    Integer updateUserPassword(User user);

    @Select("select su.* from sys_user su order by su.value")
    List<User> getAll();

    @Select("select distinct su.provinces as name,su.value from sys_user su")
    List<UserProvincesDto> getProinces();

    List<UserCountDto> getCount();
}
