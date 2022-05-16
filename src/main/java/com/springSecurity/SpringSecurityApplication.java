package com.springSecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.InetSocketAddress;

@SpringBootApplication
@EnableScheduling
public class SpringSecurityApplication{




    private static final Logger log= LoggerFactory.getLogger(SpringSecurityApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(SpringSecurityApplication.class,args);

    }


}
