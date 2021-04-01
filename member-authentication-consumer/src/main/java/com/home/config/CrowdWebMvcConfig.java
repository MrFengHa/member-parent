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
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //浏览器访问的地址
        String regUrlPath = "/auth/member/to/reg/page";
        String loginUrlPath = "/auth/member/to/login/page";
        String memberCenterUrlPath = "/auth/member/to/center/page";
        String myCrowdUrlPath = "/member/my/crowd";

        //访问视图的名称  将来拼接前后缀
        String regViewName="member-reg";
        String loginViewName="member-login";
        String memberCenterViewName="member-center";
        String myCrowdViewName="member-crowd";
        //添加一个view-controller
        registry.addViewController(regUrlPath).setViewName(regViewName);
        registry.addViewController(loginUrlPath).setViewName(loginViewName);
        registry.addViewController(memberCenterUrlPath).setViewName(memberCenterViewName);
        registry.addViewController(myCrowdUrlPath).setViewName(myCrowdViewName);
    }
}
