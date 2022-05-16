package com.springSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCountDto {

    private Integer id;

    private Integer value;

    private String name;

    private Integer ranking;


}
