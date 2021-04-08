package com.home.service.impl;

import com.home.entity.po.ProjectPO;
import com.home.entity.vo.ProjectVO;
import com.home.mapper.ProjectPOMapper;
import com.home.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/4/1 17:17
 */
@Transactional(readOnly = true)
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectPOMapper projectPOMapper;

    /**
     * 保存Project
     *
     * @param projectVO
     * @param memberId
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void saveProject(ProjectVO projectVO, Integer memberId) {
        //一、保存ProjectPO对象
        //1.创建ProjectPO对象
        ProjectPO projectPO = new ProjectPO();
        //2.把ProjectVO中的属性复制到projectPO中
        BeanUtils.copyProperties(projectVO,projectPO);
        //3.保存projectPO
        //为了能够获取到projectPO保存后的自增组件，需要到ProjectPOMapper.xml文件中进行配置
        projectPOMapper.insertSelective(projectPO);
        //4.从projectPO对象这里获取自增主键
        Integer id = projectPO.getId();

        //二、保存项目的分离关联关系信息
        //1.从ProjectVO对象中获取typeList
        List<Integer> typeIdList = projectVO.getTypeIdList();
        //三、保存项目的标签关联信息

        //四、保存项目中详情图的路径

        //五、保存项目发起人信息

        //六、保存项目回报的信息

        //七、保存项目确认信息
    }
}
