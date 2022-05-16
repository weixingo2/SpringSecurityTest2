package com.springSecurity.controller;

import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.utils.IpToAddressUtil;
import com.springSecurity.utils.IpUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping("/get")
    public String test(){
        return "success";
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletRequest request){

        //获取IP地址
        String ipAddress = IpUtils.getIpAddr(request);



         System.out.println(IpToAddressUtil.sendGet(ipAddress,"RS5BZ-NIJKQ-7OQ5H-GN4NU-4XBVS-QUF6S"));


        System.out.println(IpToAddressUtil.getCityInfo(ipAddress));

        return ipAddress;
    }

}
