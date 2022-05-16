package com.springSecurity.service.product.ProductSku.Impl;

import com.springSecurity.entity.ProductSku;
import com.springSecurity.mapper.product.productSku.ProductSkuMapper;
import com.springSecurity.service.product.ProductSku.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductSkuServiceImpl implements ProductSkuService {

    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Override
    public ProductSku getByProductId(Integer id) {
        return productSkuMapper.getByProductId(id);
    }

    @Override
    public void insert(ProductSku productSku) {

        productSkuMapper.insert(productSku);
    }

    @Override
    public void deleteIds(Map<String, Object> map) {
        productSkuMapper.deleteIds(map);
    }

    @Override
    public void update(ProductSku productSku) {
        productSkuMapper.update(productSku);
    }

    @Override
    public ProductSku getSkuId(Integer id) {
        return productSkuMapper.getSkuId(id);
    }


}
