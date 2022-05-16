package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {

    private Integer id;

    private Integer parentId;

    @NotBlank(message="菜单名称不能为空")
    private String name;

    @NotBlank(message="菜单路径不能为空")
    private String path;

    private String perms;


    private String component;

    private Integer type;

    private String icon;

    private Integer orderNum;

    private Date created;

    private Date updated;

    private Integer statu;


    private List<Menu> children=new ArrayList<>();


}
