package com.p8.inspection.data.net.download;

import android.os.Environment;
import android.text.TextUtils;

import com.p8.inspection.core.Constants;

/**
 * author : WX.Y
 * date : 2020/9/24 16:57
 * description :
 */
public class DownloadInfo {

    /* 存储位置 */
    private String savePath;
    /* 文件总长度 */
    private long contentLength;
    /* 下载长度 */
    private long readLength;

    private String fileName;

    /* 下载该文件的url */
    private String url;

    private DownloadService service;


    public DownloadService getService() {
        return service;
    }

    public void setService(DownloadService service) {
        this.service = service;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        //"http://download.sdk.mob.com/apkbus.apk";
        String downloadPath = Environment.getExternalStorageDirectory() + "/p8_inspection/download/pdf/"+url.substring(url.lastIndexOf("/")+1);
    }

    public String getDefaultLocalPath(){
        if(TextUtils.isEmpty(url)) {
            return null;
        }
        return Constants.DOWNLOAD_PATH + url.substring(url.lastIndexOf("/")+1);
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    @Override
    public String toString() {
        return "DownloadInfo{" +
                "savePath='" + savePath + '\'' +
                ", contentLength=" + contentLength +
                ", readLength=" + readLength +
                ", url='" + url + '\'' +
                '}';
    }
}
