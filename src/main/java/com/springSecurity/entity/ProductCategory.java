package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategory {


    private Integer id;

    private String categoryName;

    private Integer categoryLevel;

    private Integer parentId;

    private String icon;

    private String categorySlogan;

    private String categoryPic;

    private String categoryBgColor;


    private Integer statu;


    private Integer number;


    List<ProductCategory> children;


}
