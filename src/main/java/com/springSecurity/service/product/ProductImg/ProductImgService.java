package com.springSecurity.service.product.ProductImg;

import com.springSecurity.entity.ProductImg;

import java.util.Map;

public interface ProductImgService {

    void insert(ProductImg productImg);

    void deleteIds(Map<String, Object> map);
}
