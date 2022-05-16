package com.springSecurity.service.product.productUser.Impl;

import com.springSecurity.entity.ProductUser;
import com.springSecurity.mapper.product.productUser.ProductUserMapper;
import com.springSecurity.service.product.productUser.ProductUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductUserServiceImpl implements ProductUserService {

    @Autowired
    private ProductUserMapper productUserMapper;
    @Override
    public ProductUser getById(Integer userId) {
        return productUserMapper.getById(userId);
    }
}
