package com.home.controller;

import com.home.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/4/1 17:16
 */
@RestController
public class ProjectProviderController {
    @Autowired
    private ProjectService projectService;
}
