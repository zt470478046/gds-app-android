package app.gds.one;

/**
 * Created by gerry on 2018/8/28.
 */

public class CloabalConstant {

    //终端号
    public static final String TERMINAL = "android";

    //TOKEN失效错误码
    public static final int TOKEN_DISABLE1 = 4000;
    public static final int TOKEN_DISABLE2 = -1;
    //自定义错误码
    public static final int JSON_ERROR = -9999;
    public static final int VOLLEY_ERROR = -9998;
    public static final int TOAST_MESSAGE = -9997;
    public static final int OKHTTP_ERROR = -9996;
    public static final int NO_DATA = -9995;


    ///////////////////permission
    public static final int PERMISSION_CONTACT = 0;
    public static final int PERMISSION_CAMERA = 1;
    public static final int PERMISSION_STORAGE = 2;


    //////////////// btn_show
    public static final String TOP_BACK_BTN_CANSHOW = "btn_topshow";







    ///错误提示汇总
    public static final String ERROR_TOAST_MESSAGE = "获取数据失败，请重试";
    public static final String ERROR_TOAST_NETWORK = "当前网络环境不佳，请稍后重试";
}
