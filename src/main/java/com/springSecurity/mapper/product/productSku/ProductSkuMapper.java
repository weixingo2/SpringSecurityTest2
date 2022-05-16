package com.springSecurity.mapper.product.productSku;

import com.springSecurity.entity.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ProductSkuMapper {
    ProductSku getByProductId(Integer id);

    void insert(ProductSku productSku);

    void deleteIds(Map<String, Object> map);

    void update(ProductSku productSku);

    ProductSku getSkuId(Integer id);
}
