package com.springSecurity.security;

import com.springSecurity.entity.User;
import com.springSecurity.service.role.RoleService;
import com.springSecurity.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    @Autowired
    UserService userService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user=userService.getByUsername(username);

        if(user==null){
            throw new UsernameNotFoundException("用户名或者密码不正确");

        }
        return new AuucountUser(user.getId(),user.getUsername(),user.getPassword(),getUserAuthority(user.getId()));
    }


    /**
     * 获取用户权限信息
     * @param
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Integer userId){


        String authority=userService.getUserAuthorityInfo(userId);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);


    }
}
