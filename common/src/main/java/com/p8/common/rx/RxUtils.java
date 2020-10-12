package com.p8.common.rx;

import com.orhanobut.logger.Logger;
import com.p8.common.http.HttpError;
import com.p8.common.http.HttpResponse;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/7  16:52
 * @email : 923080261@qq.com
 * @description : Rx工具类 统一线程调度
 */
public class RxUtils {
    /**
     * 线程调度 io-main_thread
     */
    public static final ObservableTransformer DEFAULT_TRANSFORMER = upstream -> upstream.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());

    /**
     * 线程调度 io
     */
    public static final ObservableTransformer IO_TRANSFORMER = upstream -> upstream.subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io())
            .observeOn(Schedulers.io());

    /**
     * 统一线程调度
     *
     * @param <T> 工作在io线程 结果在主线程
     * @return Flowable
     */
    public static <T> ObservableTransformer<T, T> getDefaultOSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    /**
     * 统一线程调度
     *
     * @param <T> 工作在io线程 结果在主线程
     * @return Flowable
     */
    public static <T> FlowableTransformer<T, T> getDefaultSchedulers() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程调度
     *
     * @param <T> 工作结果都在io线程
     * @return Flowable
     */
    public static <T> FlowableTransformer<T, T> getIoSchedulers() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T> 对象类型
     * @return 对象
     */
    public static <T> FlowableTransformer<HttpResponse<T>, T> handleResult() {
        return new FlowableTransformer<HttpResponse<T>, T>() {
            @Override
            public Flowable<T> apply(Flowable<HttpResponse<T>> httpResponse) {
                return httpResponse.flatMap(new Function<HttpResponse<T>, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(HttpResponse<T> tHttpResponse) {
                        Logger.e("---------------------"+tHttpResponse.toString());
                        if (tHttpResponse.getCode() != 0) {
                            return createData(tHttpResponse.getData());
                        } else {
                            return Flowable.error(new HttpError("服务器返回error"));
                        }
                    }
                });
            }
        };
    }


    /**
     * 生成Flowable
     *
     * @param <T> 对象类型
     * @return Flowable
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
