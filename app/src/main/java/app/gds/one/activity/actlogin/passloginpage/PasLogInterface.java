package app.gds.one.activity.actlogin.passloginpage;

import app.gds.one.activity.actlogin.phonecheck.LoginUserInterface;
import app.gds.one.base.Contract;
import app.gds.one.entity.User;

/**
 * Created by gerry on 2018/9/5.
 */

public interface PasLogInterface extends LoginUserInterface {
    interface View extends Contract.BaseView<PasLogInterface.Presenter> {


        /**设置密码 数据处理**/
        void setPasswordFail(Integer code, String toastMessage);

        void setPasswordSuccess(Object obj);
        /**设置密码 数据处理**/
        void loginPasswordFail(Integer code, String toastMessage);

        void loginPasswordSuccess(User obj);
    }

    interface Presenter extends Contract.BasePresenter {
        void loginPassword (int country,String phone ,String password);// 密码登录/设置登录密码

        void setPassword(int country,String phone ,String password ,String token );
    }

}
