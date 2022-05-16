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
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


     @Autowired
     JwtUtils jwtUtils;

     @Autowired
     UserService userService;

     @Autowired
    UserLogService userLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {


        response.setContentType("application/json;charset=UTF-8");

        ServletOutputStream outputStream=response.getOutputStream();



        //生成jwt
        String jwt=jwtUtils.generateToken(authentication.getName());


       response.setHeader(jwtUtils.getHeader(),jwt);

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,Authorization,token");

        response.setHeader("Access-Control-Expose-Headers", jwtUtils.getHeader());


        User user=userService.getByUsername(authentication.getName());


        Map<String,Object> map=new HashMap();

         map.put("user",user);
        Result result=Result.succ(200,"登录成功",map);

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("UTF-8"));

        //获取浏览器的信息
        String ua = request.getHeader("User-Agent");

        //转成UserAgent对象

        UserAgent userAgent = UserAgent.parseUserAgentString(ua);

       //获取浏览器信息

        Browser browser = userAgent.getBrowser();

        //获取系统信息

        OperatingSystem os = userAgent.getOperatingSystem();

       //系统名称

        String system = os.getName();

        //浏览器名称

        String browserName = browser.getName();


        UserLog userLog=new UserLog();

        userLog.setLoginUser(user.getUsername());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");



        userLog.setCreated(formatter.format(new Date()));

        userLog.setIp(request.getRemoteAddr());

        userLog.setSystem(system);

        userLog.setBrowserName(browserName);

        userLog.setName("登入系统");

        userLogService.insert(userLog);

        outputStream.flush();

        outputStream.close();


    }
}
