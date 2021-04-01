package com.home.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件描述
 * 项目发起人视图显示类
 * @Author 冯根源
 * @create 2021/4/1 17:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLauchInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 简单介绍
     */
    private String descriptionSimple;
    /**
     * 详细介绍
     */
    private String descriptionDetail;
    /**
     * 联系电话
     */
    private String phoneNum;
    /**
     * 客服电话
     */
    private String serviceNum;
}
