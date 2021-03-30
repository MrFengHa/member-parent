package com.home.service.impl;

import com.home.entity.po.MemberPO;
import com.home.entity.po.MemberPOExample;
import com.home.mapper.MemberPOMapper;
import com.home.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/29 14:09
 */
@Service
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberPOMapper memberPOMapper;

    /**
     * 远程访问登录账户
     *
     * @param loginacct
     * @return
     */
    public MemberPO getMemberPOByLoginAcctRemote(String loginacct) {
        //创建Example对象
        MemberPOExample memberPOExample = new MemberPOExample();
        //创建criteria对象
        MemberPOExample.Criteria criteria = memberPOExample.createCriteria();
        //封装查询条件
        criteria.andLoginacctEqualTo(loginacct);
        //执行查询
        List<MemberPO> memberPOS = memberPOMapper.selectByExample(memberPOExample);
        //获取我们的结果
        return memberPOS.get(0);
    }

    /**
     * 保存Member
     *
     * @param memberPO
     */
    public void saveMember(MemberPO memberPO) {
        memberPOMapper.insert(memberPO);
    }
}
