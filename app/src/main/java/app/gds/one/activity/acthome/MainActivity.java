package app.gds.one.activity.acthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import app.gds.one.R;
import app.gds.one.activity.actlogin.LoginUserActivity;
import app.gds.one.base.BaseActivity;
import butterknife.BindView;

/**
 * Created by gerry on 2018/8/28.
 */

public class MainActivity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navi_view)
    NavigationView navi_view;
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
        View draw_view = navi_view.inflateHeaderView(R.layout.activity_mine_head);
        ImageView head_iv = draw_view.findViewById(R.id.icon_mine);
        head_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,TripActivity.class);
                startActivity(intent);
            }
        });
        navi_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent = new Intent();

                switch (item.getItemId()){
                    case R.id.Trip_mine:
                        intent.setClass(MainActivity.this,TripActivity.class);
                        Log.i("tag","=======行程=======");
                        startActivity(intent);
                        break;
                    case R.id.wallet_mine:
                        intent.setClass(MainActivity.this,WalletActivity.class);
                        Log.i("tag","=======钱包=======");
                        startActivity(intent);
                        break;
                    case R.id.Customer_mine:
                        intent.setClass(MainActivity.this,CustomerServiceActivity.class);
                        Log.i("tag","=======客服=======");
                        startActivity(intent);
                        break;
                    case R.id.Setup_mine:
                        intent.setClass(MainActivity.this,SetUpActivity.class);
                        Log.i("tag","=======设置=======");
                        startActivity(intent);
                        break;
                    case R.id.Enterprise_mine:
                        intent.setClass(MainActivity.this,SetUpActivity.class);
                        Log.i("tag","=======企业端=======");
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.activity_main_page;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }
}
