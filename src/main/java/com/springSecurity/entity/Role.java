package com.springSecurity.entity;

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
public class Role implements Serializable {

    private Integer id;

    private String name;

    private String code;

    private String remark;

    private Date created;

    private Date updated;

    private Integer statu;


    private List<Integer> menuIds=new ArrayList<>();



}
