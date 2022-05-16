package com.springSecurity.config;

import com.springSecurity.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Order(-1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Autowired
    private CaptchaFilter captchaFilter;



    @Bean
    JwtAuthroizationFilter jwtAuthroizationFilter() throws Exception {
        JwtAuthroizationFilter jwtAuthroizationFilter=new JwtAuthroizationFilter(authenticationManager());
   return jwtAuthroizationFilter;
    }








    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
         return new BCryptPasswordEncoder();
    }

    private static final String[] URL_WHITELIST={

            "/login",
            "/user/login",
            "/logout",
            "/user/captcha",
            "/imserver/**",
            "/ws/**",
            "/download",
            "/shop/**",
            "/send/**",
            "/email/**"

    };

     @Override
    protected void configure(HttpSecurity http) throws Exception {

        http

                .csrf().disable()
                //登录配置
                .formLogin()
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)

                .and()
                .logout()
                .logoutSuccessHandler(jwtLogoutSuccessHandler)

                 //禁用Session
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

              //配置拦截规则
                .and()
                .authorizeRequests()

                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest().authenticated()


                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)


                .and()
                .addFilter(jwtAuthroizationFilter());
//                .addFilterBefore(captchaFilter,UsernamePasswordAuthenticationFilter.class);


        http.cors();
    }


    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailService);
    }



}
