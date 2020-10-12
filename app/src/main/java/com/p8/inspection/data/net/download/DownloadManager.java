package com.p8.inspection.data.net.download;

import androidx.annotation.NonNull;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.p8.common.rx.RetryFunction;
import com.p8.inspection.BuildConfig;
import com.p8.inspection.data.net.interceptor.LoggerInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.p8.inspection.data.net.download.DownloadService.HOST;

/**
 * author : WX.Y
 * date : 2020/9/24 14:28
 * description :
 */
public class DownloadManager implements DownloadProgressListener {
    private DownloadService service;
    private Disposable mDisposable;
    DownloadInfo mDownloadInfo;
    private List<DownloadListener> mListeners;

    private DownloadManager() {
        mDownloadInfo = new DownloadInfo();
        mListeners = new ArrayList<>();

        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy
                .newBuilder()
                .tag("OKHTTP_LOG")
                .methodCount(1).showThreadInfo(false).build()) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });

        LoggerInterceptor logInterceptor = new LoggerInterceptor(Logger::d);
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        final DownloadInterceptor interceptor = new DownloadInterceptor(this);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(8, TimeUnit.SECONDS);
        builder.addInterceptor(interceptor);
        builder.addInterceptor(logInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(HOST)
                .build();
        service = retrofit.create(DownloadService.class);
    }

    public void addDownloadListener(DownloadListener listener) {
        if (listener != null) {
            if (!mListeners.contains(listener)) {
                mListeners.add(listener);
            }
        }
    }

    public void removeDownloadListener(DownloadListener listener) {
        mListeners.remove(listener);
    }

    public static DownloadManager getInstance() {
        return DownloadManagerHolder.INSTANCE;
    }

    @Override
    public void onProgress(long totalSize, long downSize, boolean done) {
        mDownloadInfo.setContentLength(totalSize);
        mDownloadInfo.setReadLength(downSize);
        Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                for (DownloadListener listener : mListeners) {
                    listener.onProgress(mDownloadInfo.getUrl(), (int) (downSize * 100 / totalSize));
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private static class DownloadManagerHolder {
        private static final DownloadManager INSTANCE = new DownloadManager();
    }

    /**
     * 开始下载
     *
     * @param url
     */
    public void start(String url) {
        mDownloadInfo.setUrl(url);
        downLoad();
    }

    /**
     * 暂停下载
     */
    public void pause() {
        if (mDisposable != null)
            mDisposable.dispose();
    }

    private void downLoad() {
        mDisposable = service.download( mDownloadInfo.getUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .retryWhen(new RetryFunction())
                .map(responseBody -> {
                    writeCache(responseBody, new File(mDownloadInfo.getDefaultLocalPath()), mDownloadInfo);
                    return mDownloadInfo;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DownloadObserver(mListeners, mDownloadInfo.getUrl()));
    }


    /**
     * 写入文件
     *
     * @param file
     * @param info
     * @throws IOException
     */
    public static void writeCache(ResponseBody responseBody, @NonNull File file, DownloadInfo info) throws IOException {
        File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            if (!dir.mkdirs()) {
                return;
            }
        }
        long allLength;
        if (info.getContentLength() == 0) {
            allLength = responseBody.contentLength();
        } else {
            allLength = info.getContentLength();
        }

        FileChannel channelOut = null;
        RandomAccessFile randomAccessFile = null;
        randomAccessFile = new RandomAccessFile(file, "rwd");
        channelOut = randomAccessFile.getChannel();
        MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                info.getReadLength(), allLength - info.getReadLength());
        byte[] buffer = new byte[1024 * 4];
        int len;
        int record = 0;
        while ((len = responseBody.byteStream().read(buffer)) != -1) {
            mappedBuffer.put(buffer, 0, len);
            record += len;
        }
        responseBody.byteStream().close();
        channelOut.close();
        randomAccessFile.close();
    }

}

