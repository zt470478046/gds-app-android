package app.gds.one.data;

/**
 * Created by gerry on 2018/8/30.
 * 请求接口
 *
 */

public interface DataSource {

    void getCountry(DataCallback loadDataCallback);




    interface DataCallback {

        void onDataLoaded(Object obj);

        void onDataNotAvailable(Integer code, String toastMessage);
    }
}
