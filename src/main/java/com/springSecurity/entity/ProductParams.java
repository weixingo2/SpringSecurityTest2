package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductParams {

    private Integer id;

    private Integer productId;

    private String productPlace;

    private String footPeriod;

    private String brand;

    private String factoryName;

    private String factoryAddress;

    private String packingMethod;

    private Integer weight;

    private String storageMethod;

    private String created;

    private String updated;
}
