package com.springSecurity.security;

import cn.hutool.core.lang.Assert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class AuucountUser implements UserDetails {


    private Integer userId;

    private String password;

    private final String username;

    private final Collection<? extends GrantedAuthority> authrities;


    private final boolean accountNonExpired;

    private final boolean accountNonLocked;

    private final boolean credentialNonExpired;

    private final boolean enabled;

    public AuucountUser(Integer userId,String username,String password,Collection<? extends GrantedAuthority> authorities){

        this(userId,username,password,true,true,true,true,authorities);
    }

    public AuucountUser(Integer userId,String username,String password,boolean enabled,boolean accountNonExpired,
                        boolean credentialNonExpired,boolean accountNonLocked,Collection<? extends GrantedAuthority> authorities){

        Assert.isTrue(username!=null&&!"".equals(username)&&password!=null,"Cannot pass null or empty values to constructor");

        this.userId=userId;

        this.username=username;

        this.password=password;

        this.enabled=enabled;

        this.accountNonExpired=accountNonExpired;

        this.credentialNonExpired=credentialNonExpired;

        this.accountNonLocked=accountNonLocked;

        this.authrities=authorities;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authrities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
