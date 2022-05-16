package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUser {

    private Integer id;

    private String username;

    private String password;

    private String nickName;

    private String realName;

    private String avatar;

    private String phone;

    private String email;

    private char sex;

    private String created;

    private String updated;

}
