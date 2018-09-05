package app.gds.one.base;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Locale;

import app.gds.one.CloabalConstant;
import app.gds.one.activity.actstart.PermissionActivity;
import app.gds.one.R;
import app.gds.one.instance.SharedPreferenceInstance;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gerry on 2018/8/28.
 */

public abstract class BaseActivity extends AutoLayoutActivity {
    private PopupWindow loadingPopup;
    protected View permissionView; //权限
    protected View notLoginView;//未登录展示
    protected ImmersionBar immersionBar;
    private TextView tvNote;

    private ViewGroup emptyView;

    protected boolean isNeedhide = true;

    private Unbinder unbinder;

    protected boolean isSetTitle = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLanguage();
        setContentView(getActivityLayoutId());
        unbinder = ButterKnife.bind(this);
        ActivityManage.addActivity(this);
        emptyView = getEmptyView();
        initPermissionView();
        initLoadingPopup();//加载框
        initNotLoginView();
        if (isImmersionBarEnabled()) initImmersionBar();

        initViews(savedInstanceState);
        obtainData();
        fillWidget();
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        ActivityManage.removeActivity(this);
        hideLoadingPopup();
        if (immersionBar != null) immersionBar.destroy();
    }

    private void initLanguage() {
        Locale l = null;
        int code = SharedPreferenceInstance.getInstance().getLanguageCode();
        if (code == 1) {
            l = Locale.CHINESE;
            //new PostFormBuilder().addHeader("Accept-Language","zh-CN,zh");
        } else if (code == 2) {
            l = Locale.ENGLISH;
            // new PostFormBuilder().addHeader("Accept-Language","en-us,en");
        } else if (code == 3) {
            l = Locale.JAPANESE;
            // new PostFormBuilder().addHeader("Accept-Language","ja-JP");
        }
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        DisplayMetrics dm = resources.getDisplayMetrics();
        config.locale = l;
        resources.updateConfiguration(config, dm);
    }

    /**
     * 初始数据加载
     */
    protected abstract void loadData();

    /**
     * 控件填充
     */
    protected abstract void fillWidget();

    /**
     * 获取本地或传递的数据
     */
    protected abstract void obtainData();

    /**
     * 初始化工作
     *
     * @param savedInstanceState
     */
    protected abstract void initViews(Bundle savedInstanceState);

    /**
     * 是否启用沉浸式
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    /**
     * 获取布局ID
     */
    protected abstract int getActivityLayoutId();

    /**
     * 获取空布局的父布局
     */
    protected ViewGroup getEmptyView() {
        return null;
    }

    /**
     * 初始化加载dialog
     */
    private void initLoadingPopup() {
        View loadingView = getLayoutInflater().inflate(R.layout.pop_loading, null);
        loadingPopup = new PopupWindow(loadingView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        loadingPopup.setFocusable(true);
        loadingPopup.setClippingEnabled(false);
        loadingPopup.setBackgroundDrawable(new ColorDrawable());
    }

    /**
     * 子类重写实现扩展设置
     */
    protected void initImmersionBar() {
        immersionBar = ImmersionBar.with(this);
        immersionBar.statusBarColor(R.color.colorPrimary);
        immersionBar.statusBarDarkFont(true,0.2f);
        immersionBar.keyboardEnable(false, WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN).init();
    }

    /**
     * 显示加载框
     */
    public void displayLoadingPopup() {
        loadingPopup.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 隐藏加载框
     */
    public void hideLoadingPopup() {
        loadingPopup.dismiss();
    }


    private void initPermissionView() {
        permissionView = getLayoutInflater().inflate(R.layout.permission_die, null);
        tvNote = permissionView.findViewById(R.id.tvNote);
        permissionView.findViewById(R.id.btToPermision).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionActivity.actionStart(BaseActivity.this);
            }
        });
    }

    private void initNotLoginView() {
        notLoginView = getLayoutInflater().inflate(R.layout.empty_not_login, null);
        notLoginView.findViewById(R.id.tvToLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ///去登录操作
//                BaseActivity.this.startActivityForResult(new Intent(BaseActivity.this, LoginActivity.class), LoginActivity.RETURN_LOGIN);
            }
        });
    }

    /**
     * 显示没有权限的布局
     */
    public void showNoPermissionView(int permission) {
        if (emptyView == null) return;
        emptyView.removeAllViews();
        emptyView.addView(permissionView);
        emptyView.setVisibility(View.VISIBLE);
        switch (permission) {
            case CloabalConstant.PERMISSION_CONTACT:
                tvNote.setText("允许LIFE访问通讯录权限，可以获得更好的体验哦。");
                break;
            case CloabalConstant.PERMISSION_CAMERA:
                tvNote.setText("允许LIFE打开相机权限，可以获得更好的体验哦。");
                break;
        }
    }

    /**
     * 处理软件盘智能弹出和隐藏
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                View view = getCurrentFocus();
                if (isNeedhide) {
//                    WonderfulKeyboardUtils.editKeyboard(ev, view, this);

                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 程序是否在前台运行
     */
    public boolean isAppOnForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }
}
