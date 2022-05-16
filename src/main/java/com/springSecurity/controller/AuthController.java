package com.springSecurity.controller;

import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.springSecurity.dto.PassDto;
import com.springSecurity.dto.UserDto;
import com.springSecurity.dto.UserLatLngCountDto;
import com.springSecurity.dto.UserLogin;
import com.springSecurity.entity.User;
import com.springSecurity.exception.CaptchaException;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.result.lang.Const;
import com.springSecurity.security.UserDetailServiceImpl;
import com.springSecurity.service.user.UserService;
import com.springSecurity.utils.JwtUtils;
import com.springSecurity.utils.RedisUtil;
import com.springSecurity.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/user")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    Producer producer;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailServiceImpl userDetailsService;

    @PostMapping("/login")
    @LimitRequest(count=30)
    public Result Login(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("code") String code,HttpServletRequest request, HttpServletResponse response) throws CaptchaException {

        HashMap<String, String> tokenMap = new HashMap<>();

        String key=request.getParameter("token");

        if(StringUtils.isBlank(code)||StringUtils.isBlank(key)){
            throw new CaptchaException("验证码错误");
        }
        System.out.println(request.getServletContext().getAttribute("code"));

        if(!code.equals(request.getServletContext().getAttribute("code"))){

          return Result.fail(405,"fail",null);
        }


        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails.isEnabled()) {

            if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                String token = jwtUtils.generateToken(userDetails.getUsername());

                tokenMap.put("Authorization", token);
            }

        }

        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        response.addHeader(jwtUtils.getHeader(), tokenMap.get("Authorization"));

        return Result.succ(200, "成功", tokenMap);

    }




    @GetMapping("/userInfo")
    @LimitRequest(count=30)
    public Result userInfo(Principal principal){

        User user=userService.getByUsername(principal.getName());
        return Result.succ(MapUtil.builder()
                .put("id",user.getId())
                .put("username",user.getUsername())
                .put("avatar",user.getAvatar())
                .put("created",user.getCreated())
                .map());
    }


    @GetMapping("/getEchart")
    @LimitRequest(count=30)
    public Result getEchart() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        List<UserDto> list=userService.getEchart();


        return Result.succ(200,"成功",list);
    }


    @GetMapping("/getMap")
    @LimitRequest(count=30)
    public Result getMap() throws ParseException {
        List<UserLatLngCountDto>  userLatLngCountDtoList=new ArrayList<>();
        String[] userLatLng=new String[3];
        UserLatLngCountDto userLatLngCountDto=new UserLatLngCountDto();
        List<UserDto> list=userService.getEchart();
        List<User> userList=userService.getAll();
        for(int i=0;i<userList.size();i++){
            userLatLng[i]=userList.get(i).getLng();
            userLatLng[i+1]=userList.get(i).getLat();
            userLatLngCountDto.setLngLat(userLatLng);
            userLatLngCountDto.setName(userList.get(i).getUsername());
            userLatLngCountDtoList.add(userLatLngCountDto);
        }
        return Result.succ(200,"成功",userLatLngCountDtoList);

    }

    @LimitRequest(count=30)

    public Result captcha(HttpServletRequest request) throws IOException {
        String key= UUID.randomUUID().toString();

        String code= producer.createText();

        BufferedImage image=producer.createImage(code);

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();

        ImageIO.write(image,"jpg",outputStream);

        BASE64Encoder encoder=new BASE64Encoder();

        String str="data:image/jpeg;base64,";

        String base64Img=str+encoder.encode(outputStream.toByteArray());

        SessionUtils.setSessionAttribute("code",code);


        request.getServletContext().setAttribute("code",code);


        return Result.succ(
                MapUtil.builder()
                        .put("key",key)
                        .put("base64Img",base64Img)
                        .build()
        );
    }


    /**
     * 修改密码
     * @return
     */
    @PostMapping("/repass")
    @Transactional
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:user:repass')")
    public Result press(@RequestParam("id")Integer id,@RequestParam("password")String password,@RequestParam("newPassword")String newPassword,@RequestParam("confirmPassword")String confirmPassword){

        //通过id获取当前用户的id，匹配是不是密码相同

        User user=userService.getUserId(id);



        if(!passwordEncoder.matches(password,user.getPassword())){

            return Result.fail(1001,"旧密码错误",null);


        }

        if(!newPassword.equals(confirmPassword)){


            return Result.fail(1002,"新密码与确认密码不同",null);


        }


        user.setPassword(passwordEncoder.encode(newPassword));

        user.setUpdated(new Date());
        userService.updateUserPassword(user);




        System.out.println(user.getPassword());

        return Result.succ(200,"成功",null);

    }



}
