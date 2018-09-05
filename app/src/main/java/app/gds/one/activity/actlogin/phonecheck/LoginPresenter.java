package app.gds.one.activity.actlogin.phonecheck;

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
    public void checkPhonestate(String country, String phone) {
        view.displayLoadingPopup();
        dataRepository.checkPhone(country, phone, new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.checkPhoneSuccess((String) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.checkPhoneFail(code, toastMessage);
            }
        });

    }

    @Override
    public void countryCode() {
        view.displayLoadingPopup();
        dataRepository.getCountry(new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
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
