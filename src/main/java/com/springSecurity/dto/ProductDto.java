package com.springSecurity.dto;

import com.springSecurity.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto extends Product {

    private String url;

    private Integer weight;

    private Integer originalPrice;

    private Integer stock;



}
