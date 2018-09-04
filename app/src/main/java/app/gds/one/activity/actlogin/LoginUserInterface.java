package app.gds.one.activity.actlogin;

import org.json.JSONObject;

import app.gds.one.base.Contract;
import app.gds.one.entity.User;

/**
 * Created by gerry on 2018/9/4.
 */

public interface LoginUserInterface {

    interface View extends Contract.BaseView<Presenter>{
        /**账号检测 数据处理**/
        void checkPhoneFail(Integer code, String toastMessage);

        void checkPhoneSuccess(JSONObject obj);

        /**账号密码登录数据处理 数据处理**/
        void loginFail(Integer code, String toastMessage);

        void loginSuccess(User obj);

        /**验证码登录数据处理 数据处理**/
        void captchSuccess(JSONObject obj);

        void captchFail(Integer code, String toastMessage);

        /**发送验证码 数据处理**/
        void sendCodeSuccess(JSONObject obj);

        void sendCodeFail(Integer code, String toastMessage);

        /** 国家代码面好获取 数据处理**/
        void countryCodeSuccess(Object obj);

        void countryCodeFail(Integer code, String toastMessage);

    }

    interface Presenter extends Contract.BasePresenter{
        void checkPhonestate();// 检测手机号状态 判断先一步操作

        void passwordLogin ();// 密码登录/设置登录密码

        void codeLogin ();//验证码登录> 设置密码

        void sendCode();//发送验证码功能

        void countryCode(); // 获取国家代码编号
    }

}
