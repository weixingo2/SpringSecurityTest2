package com.springSecurity.from;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchFrom {


    private Integer page;

    private Integer count;

    private String loginUser;

    private String ip;

    private String browserName;

    private String system;



}
