package com.home.controller;

import com.home.api.MySqlRemoteService;
import com.home.api.RedisRemoteService;
import com.home.config.ShortMessageProperties;
import com.home.entity.po.MemberPO;
import com.home.entity.vo.MemberVO;
import com.home.util.CrowdConstant;
import com.home.util.CrowdUtil;
import com.home.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
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

    @Autowired
    private MySqlRemoteService mySqlRemoteService;

    @RequestMapping("/auth/do/member/register")
    public String register(MemberVO memberVO, ModelMap modelMap) {
        //1.获取登录手机号
        String phoneNum = memberVO.getPhoneNum();
        //2.Redis中存储验证码的key
        String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
        //3.从Redis中读取key对应的value
        ResultEntity<String> resultEntity = redisRemoteService.getRedisStringValueByKey(key);
        //4.检查查询操作是否有效
        String result = resultEntity.getResult();
        if(ResultEntity.ERROR.equals(result)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,resultEntity.getMessage());
            return "member-reg";
        }
        String redisCode = resultEntity.getData();
        if (redisCode != null) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        //5.如果从Redis能够查询到value则比较表单验证码和Redis验证码
        String formCode = memberVO.getCode();

        if (!Objects.equals(redisCode, formCode)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }

        //6.如果验证码一致则从Redis删除
        redisRemoteService.removeRedisKeyRemote(key);
        //7.执行密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String userpswdBeforeEncode = memberVO.getUserpswd();
        String userpswdAfterEncode = bCryptPasswordEncoder.encode(userpswdBeforeEncode);

        memberVO.setUserpswd(userpswdAfterEncode);
        //8.执行保存
        //①创建空的MemberPO对象
        MemberPO memberPO = new MemberPO();
        //②复制属性
        BeanUtils.copyProperties(memberVO, memberPO);
        //③调用远程方法
        ResultEntity<String> saveMemberResultEntity = mySqlRemoteService.saveMember(memberPO);
        if (ResultEntity.ERROR.equals(saveMemberResultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,saveMemberResultEntity.getMessage());
            return "member-reg";
        }

        return "member-login";
    }

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
            String code = sendMessageResultEntity.getData();

            //拼接一个用于Redis存储的数据的Key
            String key = CrowdConstant.REDIS_CODE_PREFIX + phoneNum;
            System.out.println(CrowdConstant.REDIS_CODE_PREFIX + phoneNum);
            //调用远程接口存入Redis
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, 5, TimeUnit.MINUTES);

            //判断结果
            if (ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())) {
                return ResultEntity.ok();
            } else {
                return saveCodeResultEntity;
            }
        } else {
            return sendMessageResultEntity;
        }


    }
}
