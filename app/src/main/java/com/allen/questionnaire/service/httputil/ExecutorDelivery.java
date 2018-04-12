package com.allen.questionnaire.service.httputil;

import android.os.Handler;


import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.datatrasfer.WinnerResponse;

import java.util.concurrent.Executor;

/**
 * Created by Allen on 2018/1/30.
 *  请求结果回调的执行
 *  通过值主线程的Handle在主线程执行子线程请求的回调结果
 */

public class ExecutorDelivery {

    private final Executor mResponsePoster;

    public ExecutorDelivery(final Handler handler) {
        this.mResponsePoster = new Executor() {
            public void execute(Runnable command) {
                handler.post(command);
            }
        };
    }

    public <T extends WinnerResponse> void postSuccess(IDataCallBack<T> callback, T t) {
        this.mResponsePoster.execute(new ExecutorDelivery.ResponseDeliveryRunnable(0, t, callback));
    }

    public <T extends WinnerResponse> void postError(int code, String message, IDataCallBack<T> callback) {
        this.mResponsePoster.execute(new ExecutorDelivery.ResponseDeliveryRunnable(1, code, message, callback, (WinnerResponse) null));
    }

    private class ResponseDeliveryRunnable<T extends WinnerResponse> implements Runnable {
        private int code;
        private String message;
        private IDataCallBack<T> callback;
        private T t;
        private int postCode;

        public ResponseDeliveryRunnable(int postCode, int code, String message, IDataCallBack<T> callback, T t) {
            this.postCode = postCode;
            this.code = code;
            this.message = message;
            this.callback = callback;
            this.t = t;
        }

        public ResponseDeliveryRunnable(int postCode, T t, IDataCallBack<T> callback) {
            this.postCode = postCode;
            this.callback = callback;
            this.t = t;
        }

        public void run() {
            if (this.postCode == 0) {
                this.callback.onSuccess(this.t);
            } else if (this.postCode == 1) {
                this.callback.onError(this.code, this.message);
            }

        }
    }
}
