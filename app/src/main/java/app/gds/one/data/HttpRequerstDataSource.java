package app.gds.one.data;

import android.util.Log;

import com.blankj.utilcode.util.DeviceUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import app.gds.one.CloabalConstant;
import app.gds.one.entity.Country;
import app.gds.one.entity.User;
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
                    if (object.optInt("code") == 200) {
                        List<Country> objs = gson.fromJson(object.getJSONArray("data").toString(), new TypeToken<List<Country>>() {
                        }.getType());

                        dataCallback.onDataLoaded(objs);
                    } else {
                        dataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }

    @Override
    public void checkPhone(String country, String phone, DataCallback loadDataCallback) {
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.checkPhone())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .addParams("country", country)
                .addParams("phone", phone)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 200) {
                        String type = object.getJSONObject("data").getString("type").trim();
                        if (type != null && !type.equals("")) {
                            loadDataCallback.onDataLoaded(type);
                        } else {
                            loadDataCallback.onDataLoaded("0");
                        }

                    } else {
                        loadDataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }

    @Override
    public void sendCode(int country, String telephone, DataCallback loadDataCallback) {
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.sendCode())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .addParams("country", new StringBuffer().append(country).toString())
                .addParams("telephone", telephone)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 200) {
                        Log.v("MAC","response"+response);
                        loadDataCallback.onDataLoaded(object);
                    } else {
                        loadDataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }


    @Override
    public void userRegist(int country, String phone, String code, DataCallback loadDataCallback) {
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.userRegist())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .addParams("country", new StringBuffer().append(country).toString())
                .addParams("phone", phone)
                .addParams("code", code)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 200) {
                        User user = gson.fromJson(object.getJSONObject("data").toString(), User.class);
                        Log.v("MAC","user"+user.getUid());
                        loadDataCallback.onDataLoaded(user);
                    } else {
                        loadDataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }

    @Override
    public void setPassword(int country, String phone, String password, String token, DataCallback loadDataCallback) {
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.setUserPassword())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .addParams("country", new StringBuffer().append(country).toString())
                .addParams("phone", phone)
                .addParams("password", password)
                .addParams("token",token)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 200) {
//                        User user = gson.fromJson(object.getJSONObject("data").toString(), User.class);
                        Log.v("MAC","user"+response);
                        loadDataCallback.onDataLoaded(object);
                    } else {
                        loadDataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }

    @Override
    public void codeVerify(int country, String phone, String code, DataCallback loadDataCallback) {
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.codeCheck())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .addParams("country", new StringBuffer().append(country).toString())
                .addParams("phone", phone)
                .addParams("code",code)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 200) {
//                        User user = gson.fromJson(object.getJSONObject("data").toString(), User.class);
                        Log.v("MAC","user"+response);
                        loadDataCallback.onDataLoaded(object);
                    } else {
                        loadDataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }

    @Override
    public void userLogin(int country, String phone, String password, DataCallback loadDataCallback) {
        WonderfulOkhttpUtils.post().url(HttpBaseUrl.userLogin())
                .addParams("os", CloabalConstant.TERMINAL)
                .addParams("device", DeviceUtils.getAndroidID())
                .addParams("country", new StringBuffer().append(country).toString())
                .addParams("phone", phone)
                .addParams("password",password)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
            }

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject object = new JSONObject(response);
                    if (object.optInt("code") == 200) {
                        User user = gson.fromJson(object.getJSONObject("data").toString(), User.class);
                        Log.v("MAC","user"+response);
                        loadDataCallback.onDataLoaded(user);
                    } else {
                        loadDataCallback.onDataNotAvailable(object.optInt("code"), object.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    loadDataCallback.onDataNotAvailable(CloabalConstant.OKHTTP_ERROR, CloabalConstant.ERROR_TOAST_MESSAGE);
                }

            }
        });
    }
}
