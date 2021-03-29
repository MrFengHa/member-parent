package com.home.controller;

import com.home.entity.po.MemberPO;
import com.home.service.MemberService;
import com.home.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文件描述
 * 成员接口
 * @Author 冯根源
 * @create 2021/3/29 14:04
 */
@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){
        try {
            //调用本地service完成查询
            MemberPO memberPo =  memberService.getMemberPOByLoginAcctRemote(loginacct);
            //如果没有抛异常，那么就返回成功的结果
            return ResultEntity.ok().data(memberPo);
        } catch (Exception e) {
            e.printStackTrace();
            //如果捕获到异常返回失败的结果
            return ResultEntity.error(e.getMessage());
        }
    }


}
