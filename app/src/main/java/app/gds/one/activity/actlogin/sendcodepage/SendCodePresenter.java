package app.gds.one.activity.actlogin.sendcodepage;

import android.util.Log;

import org.json.JSONObject;

import app.gds.one.data.DataSource;
import app.gds.one.entity.User;

/**
 * Created by gerry on 2018/9/5.
 */

public class SendCodePresenter implements Sendcodeinterface.Presenter {
    private final DataSource dataRepository;
    private final Sendcodeinterface.View view;

    public SendCodePresenter(DataSource dataRepository, Sendcodeinterface.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void codeNext() {

    }

    @Override
    public void userRegistAction(int country, String phone, String code) {
        view.displayLoadingPopup();
        dataRepository.userRegist(country, phone,code,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                Log.v("MAC","==="+obj.toString());
                view.userRegistActionSuccess((User) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.userRegistActionFail(code, toastMessage);
            }
        });
    }

    @Override
    public void sendCode(int country, String phone) {
        view.displayLoadingPopup();
        Log.v("MAC","ohone"+phone);
        dataRepository.sendCode(country, phone,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.sendCodeSuccess((JSONObject) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.sendCodeFail(code, toastMessage);
            }
        });

    }

    @Override
    public void codeVerify(int country, String phone, String code) {
        view.displayLoadingPopup();
        Log.v("MAC","ohone"+phone);
        dataRepository.codeVerify(country, phone,code,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.codeVerifySuccess((JSONObject) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.codeVerifyFail(code, toastMessage);
            }
        });
    }
}
