package com.springSecurity.mapper.product.productOrder;

import com.springSecurity.dto.ProductOrderDto;
import com.springSecurity.entity.ProductOrder;
import com.springSecurity.vo.UserRoleOrganization;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductOrderMapper {
    List<ProductOrder> getAll1(Map<String, Object> objectMap);

    @Delete("delete po.* from product_order po where op.id=#{id}")
    void delete(Integer id);

    void insert(ProductOrderDto productOrderDto);

    @Select("select po.* from product_order po where po.userId=#{userId}")
    ProductOrder getByUserId(Integer userId);
}
