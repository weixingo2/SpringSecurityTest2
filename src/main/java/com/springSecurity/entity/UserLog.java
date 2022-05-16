package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLog {

    private Integer id;

    private String loginUser;

    private String ip;

    private String created;

    private Date lastTime;

    private String browserName;

    private String system;

    private String name;

}
