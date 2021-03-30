package com.home.util;

import com.home.common.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int random = (int) (Math.random()*10);
            builder.append(random);
        }

        String code = builder.toString();
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



    /**
     * 对明文字符串进行MD5加密
     * @param source 传入的明文字符串
     * @return
     */
    public static String md5(String source){
        // 1.判断source是否有效
        if (source==null||source.length()==0){
            //2.如果不是有效的字符串抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {
            // 3.获取MessageDigest对象
            String algorithm = "md5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            // 4.将铭文字符串对应的字节数组 执行加密
            byte[] output= messageDigest.digest(source.getBytes());
            // 5.创建BigInteger对象
            int sigNum = 1;
            BigInteger bigInteger = new BigInteger(sigNum, output);
            // 6.按照16进制将bigInteger转化为字符串
            int radix =16;
            String encoded = bigInteger.toString(radix).toUpperCase();
            return encoded;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 判断请求类型方法
     * @param request
     * @return
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1.获取请求头消息
        String acceptHeader = request.getHeader("Accept");
        String XRequestedHeader = request.getHeader("X-Requested-With");


        return ((acceptHeader != null && acceptHeader.contains("application/json"))
                ||
                (XRequestedHeader != null && XRequestedHeader.equals("XMLHttpRequest")));


    }
}
