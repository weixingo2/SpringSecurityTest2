package com.springSecurity.controller.product.productOrder;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductOrderDto;
import com.springSecurity.entity.*;
import com.springSecurity.from.OrderFrom;

import com.springSecurity.result.Result;
import com.springSecurity.service.product.ProductShopping.ProductShoppingService;
import com.springSecurity.service.product.ProductSku.ProductSkuService;
import com.springSecurity.service.product.ProductUserAddress.ProductUserAddressService;
import com.springSecurity.service.product.product.ProductService;
import com.springSecurity.service.product.productItem.ProductItemService;
import com.springSecurity.service.product.productOrder.ProductOrderService;
import com.springSecurity.service.product.productUser.ProductUserService;
import com.springSecurity.utils.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/productOrder")
public class ProductOrderController {

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private ProductItemService productItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductSkuService productSkuService;


    @Autowired
    private ProductUserService productUserService;


    @Autowired
    private ProductShoppingService productShoppingService;

    @Autowired
    private ProductUserAddressService productUserAddressService;


     @PostMapping("/get")
    public Result getAll(@RequestBody OrderFrom from, Pageable pageable){

        //获取当前用户的所有的数据
        PageInfo<ProductOrder> orders=productOrderService.getAll1(from,pageable);

        return Result.succ(200,"成功",orders);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result delete(@PathVariable("id")Integer id){

            productOrderService.delete(id);

         return Result.succ(200,"删除成功",null);
    }


    //下订单
    @PostMapping("/xiaDan")
    @Transactional
    public Result xiaDingDan(@RequestParam("ids") Integer[] ids){

         List<ProductOrderDto> list=new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

       ProductOrderDto productOrderDto=new ProductOrderDto();

        Integer totalPrice=0;

        List<Integer> countList=new ArrayList<>();

         //通过购物车的ids来找下单,来找到sku 通过sku找到商品
        for(int i=0;i<ids.length;i++){

            //通过id找到价格
            ProductShopping productShopping=productShoppingService.getById(ids[i]);

            ProductSku productSku=productSkuService.getSkuId(productShopping.getSkuId());

            //通过productSku找到商品id
            Product product=productService.getById(productSku.getProductId());

            ProductItem productItem=new ProductItem();

            productItem.setProductId(product.getId());

            productItem.setProductName(product.getProductName());

              productItem.setSkuId(productSku.getId());





            productItem.setProductPrice(productShopping.getProductPrice());

            productItem.setBuyNum(productShopping.getCartNum());



            //通过productShopping找到user

            ProductUser productUser=productUserService.getById(productShopping.getUserId());


           ProductUserAddress productUserAddress=productUserAddressService.getByUserId(productUser.getId());



            productOrderDto.setUserId(productUser.getId());

            productOrderDto.setReceiverName(productUser.getRealName());

            productOrderDto.setReceiverAddress(productUserAddress.getAddress());

            productOrderDto.setReceiverMobile(productUserAddress.getReceiverPhone());


            totalPrice=productItem.getProductPrice()*productShopping.getCartNum();



            countList.add(totalPrice);



            productOrderDto.setPayType(1);

            productOrderDto.setPayTime(formatter.format(new Date()));

            String OrderId= OrderCodeFactory.getOrderCode(productUser.getId());

            productOrderDto.setId(OrderId);

            productItem.setOrderId(OrderId);

            productItemService.insert(productItem);


        }

        Integer totalPrice1=0;

        for(int i=0;i<countList.size();i++){

            totalPrice1+=countList.get(i);

        }

        productOrderDto.setTotal(totalPrice1);

        productOrderService.insert(productOrderDto);


           return Result.succ(200,"插入订单成功",null);


    }

}
