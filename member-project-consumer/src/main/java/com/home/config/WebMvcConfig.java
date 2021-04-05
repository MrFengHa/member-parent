package com.home.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件描述
 *
 * @author 冯根源
 * @date 2021/4/5 20:29
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        //View-controller是在project-consumer内部定义的，所以这里是一个不经过Zuul访问的地址，所以不加路径前面路由规则中定义的前缀/project/
        registry.addViewController("/agree/protocol/page").setViewName("project-agree");
    }
}
