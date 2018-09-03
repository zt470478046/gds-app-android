package com.maiml.updatelibrary.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;

import com.maiml.updatelibrary.common.CommonCons;

import java.io.File;

/**
 * 更新管理器
 * <p>
 * Created by maimingliang
 */
@SuppressWarnings("unused")
public class UpdateAppManager {

    @SuppressWarnings("unused")
    private static final String TAG = "UpdateAppManager";


    private static DownloadManager downloadManager;

    /**
     * 下载Apk, 并设置Apk地址,
     * 默认位置: /storage/sdcard0/Download
     *
     * @param context    上下文
     * @param downLoadUrl 下载地址
     * @param infoName   通知名称
     * @param description  通知描述
     */
    @SuppressWarnings("unused")
    public static long downloadApk(Context context, String downLoadUrl, String description, String infoName) {

        long downloadId;
        if (!isDownloadManagerAvailable()) {
            return 0;
        }

        File downloadAPK = new File(CommonCons.APP_FILE_NAME);
        if (downloadAPK.exists()){
            downloadAPK.delete();
        }
        String appUrl = downLoadUrl;
        if (appUrl == null || appUrl.isEmpty()) {
            return 0;
        }
        appUrl = appUrl.trim(); // 去掉首尾空格
        if (!appUrl.startsWith("http")) {
            appUrl = "http://" + appUrl; // 添加Http信息
        }

        DownloadManager.Request request;
        try {
            request = new DownloadManager.Request(Uri.parse(appUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        request.setTitle(infoName);
        request.setDescription(description);
        //在通知栏显示下载进度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        //sdcard目录下的download文件夹
        request.setDestinationInExternalPublicDir(CommonCons.SAVE_APP_LOCATION, CommonCons.SAVE_APP_NAME);
        Context appContext = context.getApplicationContext();
        downloadManager = (DownloadManager) appContext.getSystemService(Context.DOWNLOAD_SERVICE);

        downloadId = downloadManager.enqueue(request);
        return downloadId;

    }

    // 最小版本号大于9
    private static boolean isDownloadManagerAvailable() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * 通过query查询下载状态，包括已下载数据大小，总大小，下载状态
     *
     * @param downloadId
     * @return
     */
    public static int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{
                -1, -1, 0
        };
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //下载状态
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }

}