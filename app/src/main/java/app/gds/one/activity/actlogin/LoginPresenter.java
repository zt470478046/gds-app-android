package app.gds.one.activity.actlogin;

import android.util.Log;

import app.gds.one.data.DataSource;

/**
 * Created by gerry on 2018/9/4.
 */

public class LoginPresenter implements LoginUserInterface.Presenter {
    private final DataSource dataRepository;
    private final LoginUserInterface.View view;

    public LoginPresenter(DataSource dataRepository, LoginUserInterface.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }
    @Override
    public void checkPhonestate() {

    }

    @Override
    public void passwordLogin() {

    }

    @Override
    public void codeLogin() {

    }

    @Override
    public void sendCode() {

    }

    @Override
    public void countryCode() {
        view.displayLoadingPopup();
        dataRepository.getCountry(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                Log.v("MAC",obj.toString());
                view.countryCodeSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.countryCodeFail(code, toastMessage);
            }
        });

    }
}
