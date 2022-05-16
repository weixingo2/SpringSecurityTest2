package com.springSecurity.mapper.product.productUserAddress;

import com.springSecurity.entity.ProductUserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductUserAddressMapper {

    @Select("select pua.* from product_user_address pua where pua.id=#{id}")
    ProductUserAddress getByUserId(Integer id);
}
