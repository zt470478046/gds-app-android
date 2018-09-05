package app.gds.one.activity.actlogin.phonecheck;

import android.app.Activity;
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

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.Serializable;
import java.util.List;

import app.gds.one.CloabalConstant;
import app.gds.one.R;
import app.gds.one.activity.actlogin.passloginpage.PasLoginPage;
import app.gds.one.activity.actlogin.sendcodepage.SendCodeActivity;
import app.gds.one.activity.pickcity.PickActivity;
import app.gds.one.base.BaseActivity;
import app.gds.one.entity.Country;
import butterknife.BindView;
import butterknife.OnClick;
import config.Injection;

/**
 * Created by gerry on 2018/8/31.
 * <p>
 * 用户登录操作
 */

public class LoginUserActivity extends BaseActivity implements LoginUserInterface.View {


    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.tvCountry)
    TextView tvCountry;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.password_login_action)
    Button passwordLoginAction;
    private boolean iscanback = true;
    private LoginUserInterface.Presenter presenter;
    private String phoneNumber = "";

    private int citycode = 86;//默认中国
    private String orlogin = "signin";//默认中国
    private final int REQUERST_CODE = 111;

    public static void actionStart(Context context, boolean canback) {
        Intent intent = new Intent(context, LoginUserActivity.class);
        Bundle bundlemsg = new Bundle();
        bundlemsg.putBoolean(CloabalConstant.TOP_BACK_BTN_CANSHOW, canback);//设置是否显示返回按钮
        intent.putExtras(bundlemsg);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void fillWidget() {
        //判断返回按钮是否显示
        if (iscanback) {
            ibBack.setVisibility(View.VISIBLE);
        } else {
            ibBack.setVisibility(View.GONE);
        }
    }

    @Override
    protected void obtainData() {

        try {
            Bundle bundle = this.getIntent().getExtras();
            iscanback = bundle.getBoolean(CloabalConstant.TOP_BACK_BTN_CANSHOW, true);
        } catch (Exception e) {
            e.printStackTrace();
            iscanback = true;
        }

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        new LoginPresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        if (KeyboardUtils.isSoftInputVisible(this)) {
            KeyboardUtils.hideSoftInput(this);
        }
        etPhone.addTextChangedListener(phonewatcher);
        //设置提币按钮 不可点击
        passwordLoginAction.setEnabled(false);
        passwordLoginAction.setTextColor(getResources().getColor(R.color.white));
        passwordLoginAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));


    }


    @Override
    protected int getActivityLayoutId() {
        return R.layout.userlogin;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUERST_CODE && resultCode == Activity.RESULT_OK) {
            Log.v("MAC", "chengshi" + data.getStringExtra("country"));
            Country countrys = Country.fromJson(data.getStringExtra("country"));
            citycode = countrys.code;
            Log.v("MAC", countrys.toString() + "CODE" + citycode + "NAME" + countrys.name);
            tvCountry.setText("+" + countrys.code);
        }

    }

    @OnClick({ R.id.ibBack,R.id.tvCountry, R.id.password_login_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCountry:
                if (KeyboardUtils.isSoftInputVisible(this)) {
                    KeyboardUtils.hideSoftInput(this);
                }
                presenter.countryCode();
                break;
            case R.id.ibBack:
                /**点击返回键 关闭窗口**/
                if (iscanback) {
                    LoginUserActivity.this.finish();
                } else {

                }

                break;
            case R.id.password_login_action:
                /**登录页面下一步**/

                phoneNumber = etPhone.getText().toString().trim();
                Log.v("MAC", "检测手机号正确姓" + phoneNumber + RegexUtils.isMobileSimple(phoneNumber));
                if (phoneNumber.equals("")) {
                    ToastUtils.showShort(R.string.input_phone_number_error);
                    return;
                }

                checkPhoneNext();

                break;
        }
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
                float volue = Float.valueOf(s.toString());
                if (volue > 0) {
                    passwordLoginAction.setEnabled(true);
                    passwordLoginAction.setTextColor(getResources().getColor(R.color.white));
                    passwordLoginAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_4a4c5a_reid5));
                } else {
                    passwordLoginAction.setEnabled(false);
                    passwordLoginAction.setTextColor(getResources().getColor(R.color.white));

                    passwordLoginAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

                }
            } else {
                passwordLoginAction.setEnabled(false);
                passwordLoginAction.setTextColor(getResources().getColor(R.color.white));

                passwordLoginAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

            }

        }
    };


    /**
     * 检测手机好吗状态
     **/
    private void checkPhoneNext() {
        ///接口请求
        presenter.checkPhonestate(new StringBuffer().append(citycode).toString(), phoneNumber);
    }

    @Override
    public void setPresenter(LoginUserInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void checkPhoneFail(Integer code, String toastMessage) {

        ToastUtils.showShort(toastMessage);
    }

    @Override
    public void checkPhoneSuccess(String obj) {
        Log.v("MAC","obj"+obj);
        if (obj.equals("0")) {
            //用户未注册调用注册接口
            SendCodeActivity.actionStart(LoginUserActivity.this,obj,phoneNumber,citycode);

        } else if (obj.equals("1")) {
            //用户未设置密码调用登录接口
            SendCodeActivity.actionStart(LoginUserActivity.this,obj,phoneNumber,citycode);

        } else if (obj.equals("2")) {
            //用户已设置密码调用密码登录

            PasLoginPage.actionStart(LoginUserActivity.this,obj,phoneNumber);
        }

    }


    @Override
    public void countryCodeSuccess(Object obj) {
        /**获取国家代码簿成功**/
        List<Country> listcountry = (List<Country>) obj;
        Intent intent = new Intent(getApplicationContext(), PickActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("country", (Serializable) listcountry);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUERST_CODE);

    }

    @Override
    public void countryCodeFail(Integer code, String toastMessage) {
        ToastUtils.showShort(toastMessage);
    }
}
