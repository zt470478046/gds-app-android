package app.gds.one.activity.actlogin.passloginpage;

import app.gds.one.data.DataSource;
import app.gds.one.entity.User;

/**
 * Created by gerry on 2018/9/5.
 */

public class PasLoginPresenter implements PasLogInterface.Presenter {
    private final DataSource dataRepository;
    private final PasLogInterface.View view;

    public PasLoginPresenter(DataSource dataRepository, PasLogInterface.View view) {
        this.dataRepository = dataRepository;
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void loginPassword(int country, String phone, String password) {
        view.displayLoadingPopup();
        dataRepository.userLogin(country, phone,password,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.loginPasswordSuccess((User) obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.loginPasswordFail(code, toastMessage);
            }
        });
    }

    @Override
    public void setPassword(int country, String phone, String password, String token) {
        view.displayLoadingPopup();
        dataRepository.setPassword(country, phone,password, token,new DataSource.DataCallback() {
            @Override
            public void onDataLoaded(Object obj) {
                view.hideLoadingPopup();
                view.setPasswordSuccess(obj);
            }

            @Override
            public void onDataNotAvailable(Integer code, String toastMessage) {
                view.hideLoadingPopup();
                view.setPasswordFail(code, toastMessage);
            }
        });
    }
}
