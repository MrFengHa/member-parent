package com.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 11:38
 */
@Controller
public class PortalController {
    @RequestMapping("/")
    public String portal(){

        return "portal";
    }
}
