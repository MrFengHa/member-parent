package com.home.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/31 11:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberLoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String username;
    private String email;
}
