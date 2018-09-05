package app.gds.one.activity.actlogin.sendcodepage;

import org.json.JSONObject;

import app.gds.one.activity.actlogin.phonecheck.LoginUserInterface;
import app.gds.one.base.Contract;
import app.gds.one.entity.User;

/**
 * Created by gerry on 2018/9/5.
 */

public interface Sendcodeinterface extends LoginUserInterface {

    interface View extends Contract.BaseView<Sendcodeinterface.Presenter>{

        /**验证码登录数据处理 数据处理**/
        void captchSuccess(JSONObject obj);

        void captchFail(Integer code, String toastMessage);

        /**发送验证码 数据处理**/
        void sendCodeSuccess(JSONObject obj);

        void sendCodeFail(Integer code, String toastMessage);

        /**验证码登录数据处理 数据处理**/
        void userRegistActionSuccess(User obj);

        void userRegistActionFail(Integer code, String toastMessage);
        /**验证码登录数据处理 数据处理**/
        void codeVerifySuccess(Object obj);

        void codeVerifyFail(Integer code, String toastMessage);
    }

    interface Presenter extends Contract.BasePresenter{

        void codeNext ();//验证码页面下一步操作

        void userRegistAction(int country, String phone, String code);

        void sendCode(int country, String phone);//发送验证码功能

        void codeVerify(int country, String phone,String code);//验证码检测
    }
}
