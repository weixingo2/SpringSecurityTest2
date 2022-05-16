package com.springSecurity.security;

import cn.hutool.core.util.StrUtil;
import com.springSecurity.entity.User;
import com.springSecurity.service.user.UserService;
import com.springSecurity.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtAuthroizationFilter extends BasicAuthenticationFilter {


     @Autowired
    JwtUtils jwtUtils;

     @Autowired
     UserDetailServiceImpl userDetailsService;


     @Autowired
     UserService userService;

    public JwtAuthroizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        if ("OPTIONS".equals(request.getMethod())){
            chain.doFilter(request,response);
            System.out.println("放行了");
            return;
        }
        String jwt = request.getHeader("Authorization");

            if(StrUtil.isBlankOrUndefined(jwt)){
                chain.doFilter(request,response);
                return;
            }
            Claims claim= jwtUtils.getClaimByToken(jwt);
            if(claim==null){
                throw new JwtException("token 异常");
            }
            if(jwtUtils.isTokenExpired(claim)){
                throw new JwtException("token 过期");
            }
            String username=claim.getSubject();
            User user=userService.getByUsername(username);

            //获取用户的权限信息
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,null,userDetailsService.getUserAuthority(user.getId()));


            SecurityContextHolder.getContext().setAuthentication(token);


            chain.doFilter(request,response);

        }


}
