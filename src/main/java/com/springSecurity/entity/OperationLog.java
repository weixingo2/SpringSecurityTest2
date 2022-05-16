package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationLog {

    private Integer id;

    private String name;

    private String operation;

    private Date time;

    private String ip;

    private Date created;



}
