package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    private Integer id;

    private String phone;

    private String organizationName;

    private Integer OrganizationNum;

    private Date created;

    private Date updated;

    private Integer statu;

    private Integer count;

    private Integer page;


}
