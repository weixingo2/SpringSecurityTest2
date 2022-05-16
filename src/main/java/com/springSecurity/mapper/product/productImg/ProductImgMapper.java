package com.springSecurity.mapper.product.productImg;

import com.springSecurity.entity.ProductImg;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ProductImgMapper {


    void insert(ProductImg productImg);

    void deleteIds(Map<String, Object> map);
}
