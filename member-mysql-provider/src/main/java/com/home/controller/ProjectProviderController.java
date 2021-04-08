package com.home.controller;

import com.home.entity.vo.ProjectVO;
import com.home.service.ProjectService;
import com.home.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/save/project/vo/remote")
    public ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, Integer memberId){
        try {
            //调用“本地”Service执行保存
            projectService.saveProject(projectVO,memberId);
            return ResultEntity.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.error(e.getMessage());
        }

    }
}
