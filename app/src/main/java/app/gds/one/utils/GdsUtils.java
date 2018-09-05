package app.gds.one.utils;

import com.blankj.utilcode.util.StringUtils;

/**
 * Created by gerry on 2018/9/5.
 */

public class GdsUtils {

    static GdsUtils gdsUtils;

    public static GdsUtils getInstance() {
        synchronized (GdsUtils.class) {
            if (null == gdsUtils) {
                gdsUtils = new GdsUtils();
            }
        }
        return gdsUtils;
    }

    public GdsUtils(){

    }


    public String sqlitPhone(String phone){
        if(!StringUtils.isEmpty(phone) && phone.length() > 6 ) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        return phone;
    }



}
