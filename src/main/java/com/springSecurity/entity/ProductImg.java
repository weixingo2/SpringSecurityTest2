package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImg {

    private Integer id;

    private Integer productId;

    private String url;

    private String sort;

    private Integer isMain;

    private String created;

    private String updated;



}
