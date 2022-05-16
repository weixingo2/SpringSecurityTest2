package com.springSecurity.from;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemFrom {

    private Integer count;

    private Integer page;

    private String productName;

}
