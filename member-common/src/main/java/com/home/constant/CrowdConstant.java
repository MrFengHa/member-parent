package com.home.constant;

/**
 * 常量类
 *
 * @author 冯根源
 * @date 2021/1/26 22:56
 */
public class CrowdConstant {

    public static final String MESSAGE_LOGIN_FAILED = "抱歉！账号密码错误！请从新输入";
    public static final String MESSAGE_LOGIN_ACCT_ALREADY_IN_USE = "抱歉！这个账号已经被使用了";
    public static final String MESSAGE_ACCESS_FORBIDDEN = "请登录后访问";
    public static final String MESSAGE_STRING_INVALIDATE = "字符串不合法,请不要传入空字符串";
    public static final String MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE = "系统错误，登录账号不唯一";
    public static final String MESSAGE_ACCESS_DENIED = "抱歉！您不能访问这个资源";
    public static final Object MESSAGE_CODE_NOT_EXISTS = "验证码不存在！，请检查手机号或者从新发送";
    public static final Object MESSAGE_CODE_INVALID = "验证码不正确";
    /**
     * 异常返回字符
     */
    public static final String ATTR_NAME_EXCEPTION = "exception";
    public static final String ATTR_NAME_LOGIN_ADMIN = "loginAdmin";
    public static final String ATTR_NAME_LOGIN_MEMBER = "loginMember";
    public static final String ATTR_NAME_PAGE_INFO = "pageInfo";
    public static final String ATTR_NAME_MESSAGE = "message";

    public static final String REDIS_CODE_PREFIX = "REDIS_CODE_PREFIX";

}
