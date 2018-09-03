package com.maiml.updatelibrary.common;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by maimingliang on 2016/10/8.
 */

public class CommonCons {

    public static final double version = 1.0;
    public final static String SAVE_APP_NAME = "download"+version+".apk";

    public final static String SAVE_APP_LOCATION = "/download";


    public final static String APP_FILE_NAME = "/sdcard"+SAVE_APP_LOCATION+ File.separator + SAVE_APP_NAME;
    public static final String DOWNLOAD_APK_ID_PREFS = "download_apk_id_prefs";
}
