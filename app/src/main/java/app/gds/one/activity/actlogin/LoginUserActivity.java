package app.gds.one.activity.actlogin;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.ToastUtils;

import org.json.JSONObject;

import app.gds.one.CloabalConstant;
import app.gds.one.R;
import app.gds.one.activity.pickcity.PickActivity;
import app.gds.one.base.BaseActivity;
import app.gds.one.entity.Country;
import app.gds.one.entity.User;
import app.gds.one.utils.customview.VerificationCodeView;
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
    @BindView(R.id.user_login)
    LinearLayout userLogin;
    @BindView(R.id.phone_password)
    EditText phonePassword;
    @BindView(R.id.forget_pas)
    TextView forgetPas;
    @BindView(R.id.user_password)
    LinearLayout userPassword;
    @BindView(R.id.phone_number_message)
    TextView phoneNumberMessage;
    @BindView(R.id.six_code)
    VerificationCodeView sixCode;
    @BindView(R.id.phonecode_login)
    LinearLayout phonecodeLogin;
    @BindView(R.id.password_action)
    Button passwordAction;
    @BindView(R.id.layout_next)
    LinearLayout layoutNext;
    @BindView(R.id.password_login_action)
    Button passwordLoginAction;
    @BindView(R.id.password_forget_action)
    Button passwordForgetAction;

    private boolean iscanback = true;
    private int type = 0; //跳转判定
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

        etPhone.addTextChangedListener(phonewatcher);
        //设置提币按钮 不可点击
        passwordLoginAction.setEnabled(false);
        passwordLoginAction.setTextColor(getResources().getColor(R.color.white));
        passwordLoginAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

        phonePassword.addTextChangedListener(passwordwatcher);
        //设置提币按钮 不可点击
        passwordForgetAction.setEnabled(false);
        passwordForgetAction.setTextColor(getResources().getColor(R.color.white));
        passwordForgetAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

        // code 输入
        sixCode.setInputCompleteListener(
                new VerificationCodeView.InputCompleteListener() {
                    @Override
                    public void inputComplete() {
                        Log.i("MAC", sixCode.getInputContent()+"lenth"+sixCode.getInputContent().length());
                        if (sixCode.getInputContent().length() == 6) {
                            passwordAction.setEnabled(true);
                            passwordAction.setTextColor(getResources().getColor(R.color.white));
                            passwordAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_4a4c5a_reid5));
                        } else {
                            passwordAction.setEnabled(false);
                            passwordAction.setTextColor(getResources().getColor(R.color.white));

                            passwordAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

                        }

                    }

                    @Override
                    public void deleteContent() {
                        Log.i("MAC", sixCode.getInputContent());
                        if (sixCode.getInputContent().length() == 6) {
                            passwordAction.setEnabled(true);
                            passwordAction.setTextColor(getResources().getColor(R.color.white));
                            passwordAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_4a4c5a_reid5));
                        } else {
                            passwordAction.setEnabled(false);
                            passwordAction.setTextColor(getResources().getColor(R.color.white));

                            passwordAction.setBackground(getResources().getDrawable(R.drawable.btn_bg_cccccc_reid5));

                        }
                    }
                }
        );
    }


    @Override
    protected int getActivityLayoutId() {
        return R.layout.userlogin;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUERST_CODE && resultCode == Activity.RESULT_OK) {
            Country countrys = Country.fromJson(data.getStringExtra("country"));
            citycode = countrys.code;
            tvCountry.setText("+" + countrys.code);
        }

    }

    @OnClick({R.id.tvCountry, R.id.ibBack, R.id.forget_pas, R.id.password_action, R.id.password_login_action, R.id.password_forget_action})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCountry:
                if(KeyboardUtils.isSoftInputVisible(this)){
                    KeyboardUtils.hideSoftInput(this);
                }
                presenter.countryCode();
                break;
            case R.id.ibBack:
                /**点击返回键 关闭窗口**/
                if (type == 0 && iscanback) {
                    LoginUserActivity.this.finish();
                } else {

                }

                break;
            case R.id.forget_pas:
                /**忘记密码点击时间**/

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
            case R.id.password_forget_action:
                /**忘记密码页面下一步**/

                break;
            case R.id.password_action:
                /**下一步操作**/


                break;
        }
    }


    private void nextAction(int flog) {
        switch (flog) {
            case 1:
                ////登录 >  密码

                break;

            case 2:
                ////登录 > 验证码


                break;

            case 3:
                /// 登录 > 密码 > 验证吗


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

    TextWatcher passwordwatcher = new TextWatcher() {

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


    }

    /***验证吗发送***/
    private void sendCode() {

    }

    /***验证吗发送 下一步***/
    private void sendCodeNext() {

    }

    private void loginPassword() {

    }


    @Override
    public void setPresenter(LoginUserInterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void checkPhoneFail(Integer code, String toastMessage) {
        LogUtils.v(code + "返回值" + toastMessage);
        ToastUtils.showShort(toastMessage);
    }

    @Override
    public void checkPhoneSuccess(JSONObject obj) {


    }

    @Override
    public void loginFail(Integer code, String toastMessage) {

    }

    @Override
    public void loginSuccess(User obj) {

    }

    @Override
    public void captchSuccess(JSONObject obj) {

    }

    @Override
    public void captchFail(Integer code, String toastMessage) {

    }

    @Override
    public void sendCodeSuccess(JSONObject obj) {

    }

    @Override
    public void sendCodeFail(Integer code, String toastMessage) {

    }

    @Override
    public void countryCodeSuccess(JSONObject obj) {
        /**获取国家代码簿成功**/
        startActivityForResult(new Intent(getApplicationContext(), PickActivity.class), REQUERST_CODE);

    }

    @Override
    public void countryCodeFail(Integer code, String toastMessage) {
        LogUtils.v(code + "返回值" + toastMessage);
        ToastUtils.showShort(toastMessage);
    }
}
