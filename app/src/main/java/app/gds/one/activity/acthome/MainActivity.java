package app.gds.one.activity.acthome;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import app.gds.one.R;
import app.gds.one.activity.actlogin.phonecheck.LoginUserActivity;
import app.gds.one.base.BaseActivity;
import butterknife.BindView;

/**
 * Created by gerry on 2018/8/28.
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn)
    Button btn;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
//        isImmersionBarEnabled();
    }

    @Override
    protected void fillWidget() {
    }

    @Override
    protected void obtainData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUserActivity.actionStart(MainActivity.this,true);
            }
        });
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main_page;
    }

}
