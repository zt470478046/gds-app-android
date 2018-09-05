package app.gds.one.activity.actlogin.phonecheck;

import app.gds.one.base.Contract;

/**
 * Created by gerry on 2018/9/4.
 */

public interface LoginUserInterface {

    interface View extends Contract.BaseView<Presenter>{
        /**账号检测 数据处理**/
        void checkPhoneFail(Integer code, String toastMessage);

        void checkPhoneSuccess(String obj);

        /** 国家代码面好获取 数据处理**/
        void countryCodeSuccess(Object obj);

        void countryCodeFail(Integer code, String toastMessage);

    }

    interface Presenter extends Contract.BasePresenter{
        void checkPhonestate(String country, String phone);// 检测手机号状态 判断先一步操作

        void countryCode(); // 获取国家代码编号
    }

}
