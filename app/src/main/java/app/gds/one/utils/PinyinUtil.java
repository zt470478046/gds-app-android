package app.gds.one.utils;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by gerry on 2018/9/4.
 */

public class PinyinUtil {

    public static String getPingYin(String inputString) {
        try {
            return Pinyin.toPinyin(inputString, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
