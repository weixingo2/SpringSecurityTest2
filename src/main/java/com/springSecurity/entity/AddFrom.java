package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddFrom {

    private Integer productId;

    private Integer productNum;

    private Integer userId;

    private Integer productPrice;
}
