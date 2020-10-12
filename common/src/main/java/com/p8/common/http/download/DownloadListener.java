package com.p8.common.http.download;

/**
 * author : WX.Y
 * date : 2020/9/18 18:16
 * description :
 */
public interface DownloadListener {
    void onStartDownload(long length);

    void onProgress(int progress);

    void onFail(String errorInfo);
}

