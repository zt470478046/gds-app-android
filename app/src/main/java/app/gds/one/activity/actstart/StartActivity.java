package app.gds.one.activity.actstart;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.gyf.barlibrary.ImmersionBar;

import java.util.Timer;
import java.util.TimerTask;

import app.gds.one.activity.acthome.MainActivity;
import app.gds.one.R;
import app.gds.one.base.BaseActivity;
import app.gds.one.instance.SharedPreferenceInstance;

/**
 * Created by gerry on 2018/8/28.
 */

public class StartActivity extends BaseActivity{
    private Timer timer;
    int n = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).fullScreen(true).init();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void fillWidget() {
        timerStart();
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_start;
    }
    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onBackPressed();

    }
    private void timerStart() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (n == 0) {
                    timer.cancel();
                    timer = null;
                    ////TODO 不需要登录即可进入主界面的应用
                    if (SharedPreferenceInstance.getInstance().getIsFirstUse()) LeadActivity.actionStart(StartActivity.this);
                    else
                        MainActivity.actionStart(StartActivity.this);
                    ////TODO 必须要登录才能进入程序主界面
//                    if (SharedPreferenceInstance.getInstance().getIsFirstUse())
//                        LeadActivity.actionStart(StartActivity.this);
//                    else if (!WonderfulStringUtils.isEmpty(MyApplication.getApp().getCurrentUser().getToken())) //没有过期
//                        MainActivity.actionStart(StartActivity.this);
//                    else
//                        LoginActivity.actionStart(StartActivity.this);
                    finish();
                }
                n--;
            }
        }, 10, 999);
    }
}
