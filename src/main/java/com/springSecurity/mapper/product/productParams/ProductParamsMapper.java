package com.springSecurity.mapper.product.productParams;

import com.springSecurity.entity.ProductParams;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ProductParamsMapper {
    ProductParams getProductParam(Integer id);

    void insert(ProductParams productParams);

    void deleteIds(Map<String, Object> map);
}
