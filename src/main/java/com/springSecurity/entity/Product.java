package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Integer id;

    private String productName;

    private Integer categoryId;

    private Integer rootCategoryId;

    private Integer soldNum;

    private Integer productStatus;

    private String content;

    private String created;

    private String updated;




}
