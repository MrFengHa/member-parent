package com.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 11:27
 */
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class AuthenticationConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationConsumerApplication.class, args);
    }
}
