package app.gds.one;

import android.app.Application;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by gerry on 2018/8/24.
 */

public class MyApplication extends Application {

    public static MyApplication app;
    /**
     * 当前是否有网络
     */
    private boolean isConnect = false;
    /**
     * 当前手机屏幕的宽高
     */
    private int mWidth;
    private int mHeight;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        Utils.init(app);
        /*二维码识别*/
        ZXingLibrary.initDisplayOpinion(this);
        getDisplayMetric();

        checkInternet();
    }

    /**
     * 获取屏幕的宽高
     */
    private void getDisplayMetric() {
        mWidth = getResources().getDisplayMetrics().widthPixels;
        mHeight = getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取程序的Application对象
     */
    public static MyApplication getApp() {
        return app;
    }

    /**
     * 检查是否有网络
     */
    private void checkInternet() {
        if (NetworkUtils.isConnected()) {
            isConnect = true;
        }

    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }
}
