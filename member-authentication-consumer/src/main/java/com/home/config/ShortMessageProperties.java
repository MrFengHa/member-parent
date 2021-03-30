package com.home.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 17:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "short.message")
@Component
public class ShortMessageProperties {
    private String host;
    private String path;
    private String method;
    private String appCode;
    private String smsSignId;
    private String templateId;
}
