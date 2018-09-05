package app.gds.one.activity.actlogin.sendcodepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;

import org.json.JSONObject;

import app.gds.one.MyApplication;
import app.gds.one.R;
import app.gds.one.activity.actlogin.passloginpage.PasLoginPage;
import app.gds.one.base.BaseActivity;
import app.gds.one.entity.User;
import app.gds.one.utils.WonderfulFileUtils;
import app.gds.one.utils.customview.VerificationCodeView;
import butterknife.BindView;
import butterknife.OnClick;
import config.Injection;

/**
 * Created by gerry on 2018/9/5.
 */

public class SendCodeActivity extends BaseActivity implements Sendcodeinterface.View {


    @BindView(R.id.ibBack)
    ImageButton ibBack;
    @BindView(R.id.phone_number_message)
    TextView phoneNumberMessage;
    @BindView(R.id.phone_code_time)
    TextView phonecodetime;
    @BindView(R.id.six_code)
    VerificationCodeView sixCode;
    @BindView(R.id.password_action)
    Button passwordAction;

    private String types = "-1";// 0  用户注册 、1 用户未设置登录密码
    private String numberphone = "";
    private int country = 86;
    private Sendcodeinterface.Presenter presenter;

    private CountDownTimer timer;

    public static void actionStart(Context context, String type, String phone, int country) {
        Intent intent = new Intent(context, SendCodeActivity.class);
        Bundle bundlemsg = new Bundle();
        bundlemsg.putString("type", type);
        bundlemsg.putString("phone", phone);
        bundlemsg.putInt("country", country);
        intent.putExtras(bundlemsg);
        context.startActivity(intent);
    }

    @Override
    protected void loadData() {
        senCode();
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
        new SendCodePresenter(Injection.provideTasksRepository(getApplicationContext()), this);
        // code 输入

        sixCode.setInputCompleteListener(
                new VerificationCodeView.InputCompleteListener() {
                    @Override
                    public void inputComplete() {
                        Log.i("MAC", sixCode.getInputContent() + "lenth" + sixCode.getInputContent().length());
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
        return R.layout.layout_sendcode;
    }


    @OnClick({R.id.ibBack, R.id.password_action, R.id.phone_code_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ibBack:
                /**点击返回键 关闭窗口**/
                SendCodeActivity.this.finish();

                break;


            case R.id.password_action:
                /**忘记密码页面下一步**/
                String code = sixCode.getInputContent();
                Log.v("MAC", "CODE" + code);
                if (types.equals("0")) {
                    //用户注册
                    presenter.userRegistAction(country, numberphone, code);
                }
                if (types.equals("1")) {
                    //验证手机号设置密码
                    presenter.codeVerify(country, numberphone, code);

                }

                break;

            case R.id.phone_code_time:

                senCode();

                break;

        }
    }

    private void fillCodeView(long time) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(time, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                phonecodetime.setText(getResources().getString(R.string.re_send) + "（" + millisUntilFinished / 1000 + "）");
            }

            @Override
            public void onFinish() {
                phonecodetime.setText(R.string.send_code);
                phonecodetime.setEnabled(true);
                timer.cancel();
                timer = null;
            }
        };
        timer.start();
    }

    @Override
    protected void onDestroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        super.onDestroy();
    }

    private void senCode() {
        presenter.sendCode(country, numberphone);
    }

    @Override
    public void setPresenter(Sendcodeinterface.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void captchSuccess(JSONObject obj) {

    }

    @Override
    public void captchFail(Integer code, String toastMessage) {

    }

    @Override
    public void sendCodeSuccess(JSONObject obj) {
        fillCodeView(60 * 1000);
        phonecodetime.setEnabled(false);

    }

    @Override
    public void sendCodeFail(Integer code, String toastMessage) {
        ToastUtils.showShort("code" + toastMessage);
        phonecodetime.setEnabled(true);
    }

    @Override
    public void userRegistActionSuccess(User obj) {
        WonderfulFileUtils.getLongSaveFile(this, "User", "user.info").delete();
        MyApplication.getApp().setCurrentUser(obj);

        PasLoginPage.actionStart(SendCodeActivity.this,types,numberphone);
    }

    @Override
    public void userRegistActionFail(Integer code, String toastMessage) {
        ToastUtils.showShort(toastMessage);
    }

    @Override
    public void codeVerifySuccess(Object obj) {
        // 检测手机号通过
        PasLoginPage.actionStart(SendCodeActivity.this,types,numberphone);
    }

    @Override
    public void codeVerifyFail(Integer code, String toastMessage) {
        ToastUtils.showShort(toastMessage);
    }
}
