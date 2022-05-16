package com.springSecurity.security;

import com.springSecurity.exception.CaptchaException;
import com.springSecurity.result.lang.Const;
import com.springSecurity.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CaptchaFilter extends OncePerRequestFilter {


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {


        String url=httpServletRequest.getRequestURI();

        if("/login".equals(url)&&httpServletRequest.getMethod().equals("POST")){

            //校验验证码
            try {
                validate(httpServletRequest);
            } catch (Exception e) {

                loginFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse, (AuthenticationException) e);
            }


            //如果不正确的，就跳到认证错误处理器

        }

filterChain.doFilter(httpServletRequest,httpServletResponse);

    }

    private void validate(HttpServletRequest httpServletRequest) throws CaptchaException {

        String code=httpServletRequest.getParameter("code");
        String key=httpServletRequest.getParameter("token");

        if(StringUtils.isBlank(code)||StringUtils.isBlank(key)){

           throw new CaptchaException("验证码错误");

        }

        if(!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,key))){

            throw new CaptchaException("验证码错误");
        }
        redisUtil.hdel(Const.CAPTCHA_KEY,key);
    }


}
