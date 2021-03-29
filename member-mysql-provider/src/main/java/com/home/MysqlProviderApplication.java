package com.home;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/24 16:51
 */
@MapperScan("com.home.mapper")
@SpringBootApplication
public class MysqlProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(MysqlProviderApplication.class, args);
    }
}
