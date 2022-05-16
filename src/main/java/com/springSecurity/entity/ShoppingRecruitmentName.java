package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingRecruitmentName {

    private Integer id;

    private String recruitmentName;

    private Integer sendTime;

    private Integer distance;

    private Integer priceSend;

    private Integer avgPerson;

    private String url;

    private Integer monthPin;

    private String title;

}
