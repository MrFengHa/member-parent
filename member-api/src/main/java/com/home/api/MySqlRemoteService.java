package com.home.api;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * mysql远程服务类
 *
 * @Author 冯根源
 * @create 2021/3/26 12:24
 */
@FeignClient("mysql-provider")
public class MySqlRemoteService {
}
