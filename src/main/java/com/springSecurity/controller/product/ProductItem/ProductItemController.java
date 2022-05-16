package com.springSecurity.controller.product.ProductItem;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductItemDto;
import com.springSecurity.from.ItemFrom;
import com.springSecurity.result.Result;
import com.springSecurity.service.product.productItem.ProductItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productItem")
public class ProductItemController {

    @Autowired
   private ProductItemService productItemService;


    @PostMapping("/getAll")
    public Result getAll(@RequestBody ItemFrom from, Pageable pageable){


        PageInfo<ProductItemDto> list=productItemService.getPage(from,pageable);

        return Result.succ(200,"成功",list);

    }

    //批量删除
    @PostMapping("/delete")
    public Result delete(@RequestBody String[] productNames){

        //通过商品的id删除相对应的数据
        List<String> proIds= Arrays.asList(productNames);

        Map<String,Object> map=new HashMap<>();

        map.put("proIds",proIds);

        productItemService.deletes(map);

        return Result.succ(200,"删除成功",null);

    }



}
