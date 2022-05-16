package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUserAddress {

    private Integer id;

    private Integer userId;

    private String receiverName;

    private String receiverPhone;

    private String province;

    private String city;

    private String area;

    private String address;

    private Integer postCode;

    private Integer status;

    private String created;

    private String updated;


}
