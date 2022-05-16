package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductShopping {

    private Integer id;

    private Integer skuId;

    private Integer userId;

    private Integer cartNum;

    private String cartTime;

    private Integer productPrice;



}
