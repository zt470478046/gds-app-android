package app.gds.one.entity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import app.gds.one.data.PyEntity;
import app.gds.one.exception.ExceptionCallback;
import app.gds.one.utils.PinyinUtil;

/**
 * Created by gerry on 2018/9/4.
 */

public class Country implements PyEntity , Serializable{
    private static final String TAG = Country.class.getSimpleName();
    public int code;
    public String name, locale, pinyin;
    private static ArrayList<Country> countries = null;

    public Country(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public static ArrayList<Country> getAll(@NonNull Context ctx, @Nullable ExceptionCallback callback) {
        if(countries != null) return countries;
        countries = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(ctx.getResources().getAssets().open("code.json")));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null)
                sb.append(line);
            br.close();
            JSONArray ja = new JSONArray(sb.toString());
            String key = getKey(ctx);
            for (int i = 0; i < ja.length(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                int flag = 0;
                String locale = jo.getString("locale");
                if(!TextUtils.isEmpty(locale)) {
                    flag = ctx.getResources().getIdentifier("flag_" + locale.toLowerCase(), "mipmap", ctx.getPackageName());
                }
                countries.add(new Country(jo.getInt("code"), jo.getString(key)));
            }

            Log.i(TAG, countries.toString());
        } catch (IOException e) {
            if(callback != null) callback.onIOException(e);
            e.printStackTrace();
        } catch (JSONException e) {
            if(callback != null) callback.onJSONException(e);
            e.printStackTrace();
        }
        return countries;
    }

    public static Country fromJson(String json){
        if(TextUtils.isEmpty(json)) return null;
        try {
            JSONObject jo = new JSONObject(json);
            return new Country(jo.optInt("code") ,jo.optString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toJson(){
        return "{\"name\":\"" + name + "\", \"code\":" + code + "}";
    }

    public static void destroy(){ countries = null; }

    private static String getKey(Context ctx) {
        String country = ctx.getResources().getConfiguration().locale.getCountry();
        return "CN".equalsIgnoreCase(country)? "zh"
                : "TW".equalsIgnoreCase(country)? "tw"
                : "HK".equalsIgnoreCase(country)? "tw"
                : "en";
    }

    private static boolean inChina(Context ctx) {
        return "CN".equalsIgnoreCase(ctx.getResources().getConfiguration().locale.getCountry());
    }

    @Override
    public int hashCode() {
        return code;
    }

    @NonNull @Override
    public String getPinyin() {
        if(pinyin == null) {
            pinyin = PinyinUtil.getPingYin(name);
        }
        return pinyin;
    }

}