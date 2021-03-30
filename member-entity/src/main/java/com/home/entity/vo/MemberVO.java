package com.home.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件描述
 *
 * @author 冯根源
 * @date 2021/3/30 23:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
    private String loginacct;
    private String userpswd;
    private String username;
    private String email;
    private String phoneNum;
    private String code;
}
