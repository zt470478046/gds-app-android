package app.gds.one;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import app.gds.one.entity.User;
import app.gds.one.utils.WonderfulFileUtils;

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
    private User currentUser = new User();
    /**
     * 当前用户信息是否发生改变
     */
    private boolean isLoginStatusChange = false;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        Utils.init(app);

        LogUtils.getConfig().setGlobalTag("MAC");
        LogUtils.getConfig().setConsoleFilter(LogUtils.V);
        /*二维码识别*/
        ZXingLibrary.initDisplayOpinion(this);
        getDisplayMetric();

        checkInternet();
        x.Ext.init(this);
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
    public User getCurrentUser() {
        return currentUser == null ? currentUser = new User() : currentUser;
    }
    public synchronized void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        saveCurrentUser();
    }

    public synchronized void saveCurrentUser() {
        try {
            File file = WonderfulFileUtils.getLongSaveFile(this, "User", "user.info");
            if (currentUser == null) {
                if (file.exists()) {
                    file.delete();
                }
                return;
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(currentUser);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public boolean isLoginStatusChange() {
        return isLoginStatusChange;
    }

    public void setLoginStatusChange(boolean loginStatusChange) {
        isLoginStatusChange = loginStatusChange;
    }

}
