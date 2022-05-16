package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSku {

    private Integer id;

    private Integer productId;

    private String skuName;

    private String skuPic;

    private String untitled;

    private Integer originalPrice;

    private String discounts;

    private Integer stock;

    private String created;

    private String updated;

    private Integer status;
}
