package app.gds.one.data;

/**
 * Created by gerry on 2018/8/30.
 * 请求接口
 *
 */

public interface DataSource {

    void getCountry(DataCallback loadDataCallback);

    void checkPhone(String country , String phone ,DataCallback loadDataCallback);

    void sendCode(int country , String telephone ,DataCallback loadDataCallback);

    void userRegist(int country , String phone ,String code, DataCallback loadDataCallback);

    void setPassword(int country,String phone ,String password ,String token ,DataCallback loadDataCallback);

    void codeVerify(int country , String phone ,String code, DataCallback loadDataCallback);

    void userLogin (int country,String phone ,String password,DataCallback loadDataCallback);

    interface DataCallback {

        void onDataLoaded(Object obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }
}
