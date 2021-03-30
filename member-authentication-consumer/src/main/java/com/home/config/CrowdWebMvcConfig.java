package com.home.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 16:39
 */
@Configuration
public class CrowdWebMvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器访问的地址
        String urlPath = "/auth/member/to/reg/page.html";

        //访问视图的名称  将来拼接前后缀
        String viewName="member-reg";
        //添加一个view-controller
        registry.addViewController(urlPath).setViewName(viewName);
    }
}
