package app.gds.one.data;

import com.blankj.utilcode.util.DeviceUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.gds.one.CloabalConstant;
import app.gds.one.entity.Country;
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
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.getCountryUrl())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                dataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if(object.optInt("code") == 200){
                        List<Country> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<Country>>() {
                        }.getType());

                        dataCallback.onDataLoaded(objs);
                    }else {
                        dataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }
}
