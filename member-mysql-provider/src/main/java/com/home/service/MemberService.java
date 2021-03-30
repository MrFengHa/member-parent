package com.home.service;

import com.home.entity.po.MemberPO;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/29 14:08
 */
public interface MemberService {

    /**
     * 远程访问登录账户
     * @param loginacct
     * @return
     */
    MemberPO getMemberPOByLoginAcctRemote(String loginacct);

    /**
     * 保存Member
     * @param memberPO
     */
    void saveMember(MemberPO memberPO);
}
