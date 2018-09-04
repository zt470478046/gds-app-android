package app.gds.one.data;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;

import app.gds.one.CloabalConstant;
import app.gds.one.factory.HttpBaseUrl;
import app.gds.one.utils.okhttp.StringCallback;
import app.gds.one.utils.okhttp.WonderfulOkhttpUtils;
import okhttp3.Request;

/**
 * Created by gerry on 2018/8/30.
 * 实现请求接口数据
 */

public class HttpRequerstDataSource implements DataSource {

    private static HttpRequerstDataSource INSTANCE;

    public static HttpRequerstDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HttpRequerstDataSource();
        }
        return INSTANCE;
    }

    public HttpRequerstDataSource() {
        super();
    }


    @Override
    public void getCountry(final DataCallback dataCallback) {
        LogUtils.v("MAC","device"+DeviceUtils.getAndroidID());
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.getCountryUrl())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, null);
            }

            @Override
            public void onResponse(String response) {

                LogUtils.v("城市数据"+response);
            }
        });
    }
}
