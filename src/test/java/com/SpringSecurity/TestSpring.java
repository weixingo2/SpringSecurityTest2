package com.SpringSecurity;

import com.alibaba.fastjson.JSON;
import com.springSecurity.dto.UserCountDto;
import com.springSecurity.service.user.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestSpring {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String SCORE_RANK = "score_rank";

    @Autowired
    UserService userService;

    @Test
    public void test(){


        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();


        List<UserCountDto> list=userService.getCount();



    }

}
