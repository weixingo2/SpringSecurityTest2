package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUserHistory {

    private Integer id;

    private String area;

    private String country;

    private Integer userId;

    private String ip;

    private String created;

}
