package com.springSecurity.service.product.ProductSku;

import com.springSecurity.entity.ProductSku;

import java.util.Map;

public interface ProductSkuService {
    ProductSku getByProductId(Integer id);

    void insert(ProductSku productSku);


    void deleteIds(Map<String, Object> map);

    void update(ProductSku productSku);

    ProductSku getSkuId(Integer id);
}
