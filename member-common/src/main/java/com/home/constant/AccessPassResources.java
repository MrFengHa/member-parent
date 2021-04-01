package com.home.constant;

import com.home.constant.CrowdConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/31 17:27
 */
public class AccessPassResources {
    public static final Set<String> PASS_RES_SET = new HashSet<String>();

    static {
        PASS_RES_SET.add("/");
        PASS_RES_SET.add("/auth/member/to/reg/page");
        PASS_RES_SET.add("/auth/member/to/login/page");
        PASS_RES_SET.add("/auth/member/to/center/page");
        PASS_RES_SET.add("/auth/member/logout");
        PASS_RES_SET.add("/auth/member/do/login");
        PASS_RES_SET.add("/auth/do/member/register");

    }

    public static final Set<String> STATIC_RES_SET = new HashSet<String>();

    static {
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    /**
     * 用于判断某个ServletPath值是否对应一个静态资源
     * @param servletPath
     * @return
     */
    public static boolean judgeCurrentServletPathWetherStaticResource(String servletPath) {
        //1.排除字符串无效的情况
        if (servletPath == null || servletPath.length() == 0) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        //2.根据“/”才分ServletPath字符串
        String[] spit = servletPath.split("/");
        //3.考虑到第一个斜杠左边经过拆分后得到一个空字符传是数组的第一个元素，所以需要使用下标1  取第二个元素
        String s = spit[1];
        //判断元素是否在集合中
        return STATIC_RES_SET.contains(s);
    }
}
