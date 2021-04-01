package com.home.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 文件描述
 * 项目确认信息视图表
 * @Author 冯根源
 * @create 2021/4/1 17:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfirmInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 易付宝账号
     */
    private String paynum;
    /**
     * 法人身份证号
     */
    private String cardnum;
}
