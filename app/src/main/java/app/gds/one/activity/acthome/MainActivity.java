package app.gds.one.activity.acthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import app.gds.one.R;
import app.gds.one.base.BaseActivity;

/**
 * Created by gerry on 2018/8/28.
 */

public class MainActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
        isImmersionBarEnabled();
    }

    @Override
    protected void fillWidget() {
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main_page;
    }
}
