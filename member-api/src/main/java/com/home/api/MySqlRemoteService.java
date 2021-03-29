package com.home.api;

import com.home.entity.po.MemberPO;
import com.home.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * mysql远程服务类
 *
 * @Author 冯根源
 * @create 2021/3/26 12:24
 */
@FeignClient("mysql-provider")
public interface MySqlRemoteService {
    @RequestMapping("get/memberpo/by/login/acct/remote")
    ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);

}
