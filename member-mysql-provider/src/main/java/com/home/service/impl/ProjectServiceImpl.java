package com.home.service.impl;

import com.home.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/4/1 17:17
 */
@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {
}
