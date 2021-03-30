package com.home;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述
 *
 * @Author 冯根源
 * @create 2021/3/30 14:30
 */
public class ShortMessageTest {
    public static void main(String[] args) {
        /**
         * 需要访问的地址
         */
        String host = "https://gyytz.market.alicloudapi.com";
        /**
         * 具有短信功能的地址
         */
        String path = "/sms/smsSend";
        /**
         * 请求方式
         */
        String method = "POST";
        /**
         * 登录阿里云进入控制台找到已购买的AppCode
         */
        String appcode = "010ca100e97b4b2dae6e8154065143e3";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        /**
         * 封装其他的参数
         */
        Map<String, String> querys = new HashMap<String, String>();
        /**
         * 接收的手机号
         */
        querys.put("mobile", "17684372014");
        /**
         * 短信模板替换量
         */
        querys.put("param", "**code**:12345,**minute**:5");
        /**
         * 签名ID 签名Id 测试使用2e65b1bb3d054466b82f0c9d125465e2
         */
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        /**
         * 模板Id 测试使用908e94ccf08b4476ba6c876d13f084ad
         */
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
