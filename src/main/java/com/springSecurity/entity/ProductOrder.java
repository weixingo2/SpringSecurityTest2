package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {

    private String id;

    private Integer userId;

    private String untitled;

    private String receiverName;

    private String receiverMobile;

    private String receiverAddress;



    private Integer total;

    private Integer actualAmount;

    private Integer payType;

    private String orderMark;

    private Integer statu;

    private Integer deliveryType;

    private String deliveryFlowId;

    private Integer deleteStatu;

    private String created;

    private String updated;

    private String payTime;

    private String deliveryTime;

    private String flushTime;

    private String cancelTime;

    private Integer closeType;






}
