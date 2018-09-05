package app.gds.one.factory;

/**
 * Created by gerry on 2018/8/30.
 * <p>
 * 配置接口请求链接地址
 */

public class HttpBaseUrl {
    ///https://api.gds.wss.one/v1/country
    public static final String host = "https://api.gds.one";


    /**
     * 登录注册模块相关
     **/
    public static String getCountryUrl() {
        return host + "/v1/country";
    }

    /**
     * 检测手机号状态模块相关
     **/
    public static String checkPhone() {
        return host + "/v1/user/check";
    }

    /**
     * 发送验证码
     **/
    public static String sendCode() {
        return host + "/v1/sms";
    }

    /**
     * 用户注册接口
     **/
    public static String userRegist() {
        return host + "/v1/user/register";
    }

    /**
     * 用户设置密码
     **/
    public static String setUserPassword() {
        return host + "/v1/user/password";
    }

    /**
     * 验证码检测是否通过
     **/
    public static String codeCheck() {
        return host + "/v1/user/verify";
    }

    /**
     * 登录URL
     **/
    public static String userLogin() {
        return host + "/v1/user/login";
    }
}
