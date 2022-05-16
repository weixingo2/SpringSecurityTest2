package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoopingCategory {

    private Integer id;

    private Integer parentId;

    private Integer level;

    private String cateGoryName;

    private String url;

    private String recuritmentName;

    private Integer monthPin;

    private Integer price;

    private Integer personNum;

    private String title;

    List<ShoopingCategory> children;

    private String shoppingName;

    private Integer number;

    private Integer cartId;

    private boolean checked;
}
