package com.home.service;

import com.home.entity.vo.ProjectVO;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/4/1 17:16
 */
public interface ProjectService {
    /**
     * 保存Project
     * @param projectVO
     * @param memberId
     */
    void saveProject(ProjectVO projectVO, Integer memberId);
}
