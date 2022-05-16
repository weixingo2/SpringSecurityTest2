package com.springSecurity.mapper.product.productUser;

import com.springSecurity.entity.ProductUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductUserMapper {

    @Select("select pu.* from product_user pu where pu.id=#{userId}")
    ProductUser getById(Integer userId);
}
