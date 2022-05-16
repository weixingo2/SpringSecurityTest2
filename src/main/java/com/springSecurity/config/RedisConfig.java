package com.springSecurity.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

       RedisTemplate<Object,Object> template=new RedisTemplate<Object,Object>();

       template.setConnectionFactory(redisConnectionFactory);

        FastJsonRedisSerializer serializer=new FastJsonRedisSerializer(Object.class);

        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());

        template.setValueSerializer(serializer);


        //Hash的key也采用了StringRedisSerializer的序列化方式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        template.afterPropertiesSet();

        return  template;
    }

}
