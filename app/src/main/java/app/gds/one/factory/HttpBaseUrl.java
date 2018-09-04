package app.gds.one.factory;

/**
 * Created by gerry on 2018/8/30.
 *
 * 配置接口请求链接地址
 */

public class HttpBaseUrl {
///https://api.gds.wss.one/v1/country
    public static final String host = "https://api.gds.one";


    /**登录注册模块相关**/
    public static String getCountryUrl(){
        return host+"/v1/country";
    }


}
