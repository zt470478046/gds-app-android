package app.gds.one.activity.actlogin.passloginpage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import app.gds.one.MyApplication;
import app.gds.one.R;
import app.gds.one.activity.actlogin.sendcodepage.SendCodeActivity;
import app.gds.one.base.BaseActivity;
import app.gds.one.entity.User;
import app.gds.one.utils.WonderfulFileUtils;
import butterknife.BindView;
import butterknife.OnClick;
import config.Injection;

/**
 * Created by gerry on 2018/9/5.
 */

public class PasLoginPage extends BaseActivity implements PasLogInterface.View {
    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.phone_password)
    EditText phonePassword;
    @BindView(R.id.password_forget_action)
    Button passwordForgetAction;
    @BindView(R.id.forget_pas)
    TextView forgetpas;

    private PasLogInterface.Presenter presenter;
    private String types = "-1";// 0  用户注册 、1 用户未设置登录密码
    private String numberphone = "";
    private int country = 86;


    public static void actionStart(Context context, String type, String phone) {
        Intent intent = new Intent(context, PasLoginPage.class);
        Bundle bundlemsg = new Bundle();
        bundlemsg.putString("type", type);
        bundlemsg.putString("phone", phone);
        intent.putExtras(bundlemsg);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void fillWidget() {

    }

    @Override
    protected void obtainData() {
        try {
            Bundle bundle = this.getIntent().getExtras();
            types = bundle.getString("type", "-1");
            numberphone = bundle.getString("phone", "");
            country = bundle.getInt("country", 86);
        } catch (Exception e) {
            e.printStackTrace();
            types = "-1";
            numberphone = "";
            country = 86;
        }
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        new PasLoginPresenter(Injection.provideTasksRepository(getApplicationContext()), this);

        phonePassword.addTextChangedListener(phonewatcher);
        //设置提币按钮 不可点击
        passwordForgetAction.setEnabled(false);
        passwordForgetAction.setTextColor(getResources().getColor(R.color.white));
        passwordForgetAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));


    }

    @Override
    protected int getActivityLayoutId() {
        return R.layout.layout_paspage;
    }

    TextWatcher phonewatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //只要编辑框内容有变化就会调用该方法，s为编辑框变化后的内容
            Log.i("onTextChanged", s.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //编辑框内容变化之前会调用该方法，s为编辑框内容变化之前的内容
            Log.i("beforeTextChanged", s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {
            //编辑框内容变化之后会调用该方法，s为编辑框内容变化后的内容
            Log.i("afterTextChanged", s.toString());
            if (s.toString() != null && !s.toString().equals("")) {
                int volue = s.length();
                if (volue >= 8 && volue <= 16) {
                    passwordForgetAction.setEnabled(true);
                    passwordForgetAction.setTextColor(getResources().getColor(R.color.white));
                    passwordForgetAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_4a4c5a_reid5));
                } else {
                    passwordForgetAction.setEnabled(false);
                    passwordForgetAction.setTextColor(getResources().getColor(R.color.white));

                    passwordForgetAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

                }
            } else {
                passwordForgetAction.setEnabled(false);
                passwordForgetAction.setTextColor(getResources().getColor(R.color.white));

                passwordForgetAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

            }

        }
    };


    @OnClick({R.id.ibBack, R.id.password_forget_action, R.id.forget_pas})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ibBack:

                PasLoginPage.this.finish();

                break;
            case R.id.password_forget_action:
                Log.v("MAC", "types" + types);
                if (types.equals("0")) {
                    // 设置密码接口
                    int uid = MyApplication.getApp().getCurrentUser().getUid();
                    String token = MyApplication.getApp().getCurrentUser().getToken();
                    Log.v("MAC", "UID" + uid + "token:" + token);
                    String password = phonePassword.getText().toString().trim();
                    presenter.setPassword(country, numberphone, password, token);
                }
                if (types.equals("1")) {
                    String password = phonePassword.getText().toString().trim();
                    presenter.setPassword(country, numberphone, password, "");
                }
                if (types.equals("2")) {
                    ///登录接口
                    String password = phonePassword.getText().toString().trim();
                    presenter.loginPassword(country, numberphone, password);
                }

                break;

            case R.id.forget_pas:
                SendCodeActivity.actionStart(PasLoginPage.this,"1",numberphone,country);
                PasLoginPage.this.finish();
                break;
        }
    }

    @Override
    public void setPresenter(PasLogInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setPasswordFail(Integer code, String toastMessage) {
        ToastUtils.showShort(toastMessage);
    }

    @Override
    public void setPasswordSuccess(Object obj) {
        Log.v("MAC", "obg" + obj);

        //设置密码成功跳转手页面或者关闭本页面
        ToastUtils.showShort("设置密码成功跳转手页面或者关闭本页面");

    }

    @Override
    public void loginPasswordFail(Integer code, String toastMessage) {
        ToastUtils.showShort(toastMessage);
    }

    @Override
    public void loginPasswordSuccess(User obj) {
        User user = obj;

        WonderfulFileUtils.getLongSaveFile(this, "User", "user.info").delete();
        MyApplication.getApp().setCurrentUser(obj);
        ToastUtils.showShort(user.getPhone() + "设置密码成功跳转手页面或者关闭本页面");


    }
}
