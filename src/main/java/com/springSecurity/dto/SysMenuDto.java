package com.springSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuDto implements Serializable {

    private Integer id;

    private String name;
    
    private String title;

    private String icon;

    private String path;

    private String component;



    private List<SysMenuDto> children=new ArrayList<>();
}
