package com.home.util;

import com.home.common.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 15:50
 */
public class CrowdUtil {
    /**
     * 给远程第三方短信接口发送请求
     * @param host   需要访问的地址
     * @param path   具有短信功能的地址
     * @param method  请求方式
     * @param phoneNum 接收验证码的手机号
     * @param appCode  用来调用第三方的appCode
     * @param smsSignId    签名Id
     * @param templateId    模板Id
     * @return 返回调用结果是否成功，以及失败消息
     */
    public static ResultEntity<String> sendCodeByShortMessage(
            String host,
            String path,
            String method,
            String phoneNum,
            String appCode,
            String smsSignId,
            String templateId) {

        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appCode);
        /**
         * 封装其他的参数
         */
        Map<String, String> querys = new HashMap<String, String>();

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random()*10);
            code.append(random);
        }
        /**
         * 接收的手机号
         */
        querys.put("mobile", phoneNum);
        /**
         * 短信模板替换量
         */
        querys.put("param", "**code**:"+code+",**minute**:5");
        /**
         * 签名ID 签名Id 测试使用2e65b1bb3d054466b82f0c9d125465e2
         */
        querys.put("smsSignId", smsSignId);
        /**
         * 模板Id 测试使用908e94ccf08b4476ba6c876d13f084ad
         */
        querys.put("templateId",templateId);
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(EntityUtils.toString(response.getEntity()));

            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();

            String reasonPhrase = statusLine.getReasonPhrase();

            if (statusCode==200){
                return ResultEntity.ok().data(code);
            }
            return ResultEntity.error(reasonPhrase);

        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.error(e.getMessage());
        }
    }
}
