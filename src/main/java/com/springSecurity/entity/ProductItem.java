package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductItem {


    private String id;

    private String orderId;

    private Integer productId;

    private String productName;

    private String productImg;

    private Integer skuId;

    private String skuName;

    private Integer productPrice;

    private Integer buyNum;

    private Integer totalAmount;

    private String basketDate;

    private String buyTime;

    private Integer isComment;

}
