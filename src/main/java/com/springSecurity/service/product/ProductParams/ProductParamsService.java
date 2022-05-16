package com.springSecurity.service.product.ProductParams;


import com.springSecurity.entity.ProductParams;

import java.util.Map;

public interface ProductParamsService {
    ProductParams getProductParam(Integer id);

    void insert(ProductParams productParams);

    void deleteIds(Map<String, Object> map);
}
