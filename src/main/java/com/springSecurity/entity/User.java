package com.springSecurity.entity;

import com.alibaba.fastjson.JSONArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private Integer id;

    private String username;

    private String password;

    private String avatar;

    private String email;

    private String city;

    private Date lastLogin;

    private Integer statu;

    private Date updated;

    private String created;

    private String address;

    private String Lat;

    private String Lng;

    private String phone;

    private Integer organizationId;

    private String provinces;

    private String LatLng;


    private String OrganizationName;



    private List<Role> role=new ArrayList<>();


}
