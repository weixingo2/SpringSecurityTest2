package com.springSecurity.controller.product.productShopping;

import com.springSecurity.entity.AddFrom;
import com.springSecurity.entity.ProductShopping;
import com.springSecurity.entity.ProductSku;
import com.springSecurity.result.Result;
import com.springSecurity.service.product.ProductShopping.ProductShoppingService;
import com.springSecurity.service.product.ProductSku.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class ProductShoppingController {

    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductShoppingService productShoppingService;

     @PostMapping("/add")
     @PreAuthorize("hasAuthority('sys:user:update')")
    public Result addShopping(@RequestBody AddFrom from){

         ProductShopping productShopping=new ProductShopping();

         productShopping.setUserId(from.getUserId());

         productShopping.setCartNum(from.getProductNum());

         //根据商品的id

         ProductSku  productSku=productSkuService.getByProductId(from.getProductId());

         productShopping.setSkuId(productSku.getId());

         productShopping.setCartNum(from.getProductNum());




         productSku.setStock(productSku.getStock()-from.getProductNum());

         SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");



         productSku.setUpdated(formatter.format(new Date()));

         productShopping.setProductPrice(productSku.getOriginalPrice());

         productShopping.setCartTime(formatter.format(new Date()));

         productShoppingService.insert(productShopping);

         productSkuService.update(productSku);

         return Result.succ(200,"添加成功",null);

    }



}
