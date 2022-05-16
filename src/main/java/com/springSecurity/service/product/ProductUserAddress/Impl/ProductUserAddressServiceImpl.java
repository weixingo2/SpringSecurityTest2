package com.springSecurity.service.product.ProductUserAddress.Impl;

import com.springSecurity.entity.ProductUserAddress;
import com.springSecurity.mapper.product.productUserAddress.ProductUserAddressMapper;
import com.springSecurity.service.product.ProductUserAddress.ProductUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductUserAddressServiceImpl implements ProductUserAddressService {

    @Autowired
    private ProductUserAddressMapper productUserAddressMapper;
    @Override
    public ProductUserAddress getByUserId(Integer id) {
        return productUserAddressMapper.getByUserId(id);
    }
}
