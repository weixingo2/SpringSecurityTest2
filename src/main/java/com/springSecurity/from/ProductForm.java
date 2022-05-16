package com.springSecurity.from;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {

    private Integer[] category;

    private String brand;

    private String content;

    private String factoryAddress;

    private String factoryName;

    private String[] manyTableData;

    private Integer originalPrice;

    private String productName;

    private String productPlace;

    private Integer stock;

    private String url;

    private Integer weight;



}
