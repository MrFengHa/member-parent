package com.home.controller;

import com.home.api.RedisRemoteService;
import com.home.config.ShortMessageProperties;
import com.home.util.CrowdConstant;
import com.home.util.CrowdUtil;
import com.home.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 17:15
 */
@Controller
public class MemberController {

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @RequestMapping("/auth/member/send/short/message")
    @ResponseBody
    public ResultEntity<String> sendMessage(@RequestParam("phoneNum") String phoneNum) {
        //1.发送验证码到PhoneNum手机
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getMethod(),
                phoneNum,
                shortMessageProperties.getAppCode(),
                shortMessageProperties.getSmsSignId(),
                shortMessageProperties.getTemplateId());
        //2.判断短信发送的结果
        if (ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())) {
            //3.如果发送成功，则将验证码存入redis
            //获取随机生成的验证码
            String code =sendMessageResultEntity.getData();

            //拼接一个用于Redis存储的数据的Key
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            System.out.println(CrowdConstant.REDIS_CODE_PREFIX + phoneNum);
            //调用远程接口存入Redis
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 5, TimeUnit.MINUTES);

            //判断结果
            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
                return ResultEntity.ok();
            }else{
                return saveCodeResultEntity;
            }
        }else {
            return sendMessageResultEntity;
        }


    }
}
