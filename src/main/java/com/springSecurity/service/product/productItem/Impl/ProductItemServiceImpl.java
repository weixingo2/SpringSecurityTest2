package com.springSecurity.service.product.productItem.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductItemDto;
import com.springSecurity.entity.ProductItem;
import com.springSecurity.from.ItemFrom;
import com.springSecurity.mapper.product.productItem.ProductItemMapper;
import com.springSecurity.service.product.productItem.ProductItemService;
import com.springSecurity.utils.ObjToMap;
import com.springSecurity.vo.UserRoleOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductItemServiceImpl implements ProductItemService {

    @Autowired
    private ProductItemMapper productItemMapper;

    @Override
    public void insert(ProductItem productItem) {

        productItemMapper.insert(productItem);
    }

    @Override
    public PageInfo<ProductItemDto> getPage(ItemFrom from, Pageable pageable) {
        Map<String, Object> objectMap = ObjToMap.getObjectMap(from);




        List<ProductItemDto> productItemList=productItemMapper.getAll1(objectMap);

        PageHelper.startPage(from.getPage(), from.getCount());
        return new PageInfo<>(productItemList);
    }

    @Override
    public void deletes(Map<String, Object> map) {

        productItemMapper.deletes(map);

    }
}
