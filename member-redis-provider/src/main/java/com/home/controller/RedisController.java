package com.home.controller;

import com.home.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 10:37
 */
@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("set/redis/key/value/remote")
    ResultEntity<String> setRedisKeyValueRemote(
            @RequestParam("key") String key,
            @RequestParam("value") String value) {
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            return ResultEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.error("失败");
        }
    }


    @RequestMapping("set/redis/key/value/remote/with/timeout")
    ResultEntity<String> setRedisKeyValueRemoteWithTimeout(
            @RequestParam("key") String key,
            @RequestParam("value") String value,
            @RequestParam("time") Long time,
            @RequestParam("timeUnit") TimeUnit timeUnit) {


        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key, value,time,timeUnit);
            return ResultEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.error(e.getMessage());
        }
    }

    @RequestMapping("/get/redis/string/value/by/key")
    ResultEntity<String> getRedisStringValueByKey(@RequestParam("key") String key) {

        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            return ResultEntity.ok().data(operations.get(key));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.error(e.getMessage());
        }
    }

    @RequestMapping("/remove/redis/key/remote")
    ResultEntity<String> removeRedisKeyRemote(@RequestParam("key") String key) {

        try {
            redisTemplate.delete(key);
            return  ResultEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return  ResultEntity.error(e.getMessage());
        }
    }
}
