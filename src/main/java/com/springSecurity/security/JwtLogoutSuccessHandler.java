package com.springSecurity.security;

import cn.hutool.json.JSONUtil;
import com.springSecurity.entity.User;
import com.springSecurity.entity.UserLog;
import com.springSecurity.result.Result;
import com.springSecurity.service.log.userLog.UserLogService;
import com.springSecurity.service.user.UserService;
import com.springSecurity.utils.JwtUtils;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private UserLogService userLogService;

    @Autowired
    private UserService userService;


    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if(authentication!=null){

            new SecurityContextLogoutHandler().logout(httpServletRequest,response,authentication);

        }


        response.setContentType("application/json;charset=UTF-8");

        ServletOutputStream outputStream=response.getOutputStream();

        //生成jwt
        String jwt=jwtUtils.generateToken(authentication.getName());

        User user=userService.getByUsername(authentication.getName());

        response.setHeader(jwtUtils.getHeader(),"");


        Result result=Result.succ(200,"退出成功",null);
//
//        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));
//
//
//        //获取浏览器的信息
//        String ua = httpServletRequest.getHeader("User-Agent");
//
//        //转成UserAgent对象
//
//        UserAgent userAgent = UserAgent.parseUserAgentString(ua);
//
//        //获取浏览器信息
//
//        Browser browser = userAgent.getBrowser();
//
//        //获取系统信息
//
//        OperatingSystem os = userAgent.getOperatingSystem();
//
//        //系统名称
//
//        String system = os.getName();
//
//        //浏览器名称
//
//        String browserName = browser.getName();
//
//
//        UserLog userLog=new UserLog();
//
//        userLog.setLoginUser(user.getUsername());
//
//        userLog.setCreated(new Date());
//
//        userLog.setIp(httpServletRequest.getRemoteAddr());
//
//        userLog.setSystem(system);
//
//        userLog.setBrowserName(browserName);
//
//        userLog.setName("退出系统");
//
//        userLogService.insert(userLog);


        outputStream.flush();

        outputStream.close();
    }
}
