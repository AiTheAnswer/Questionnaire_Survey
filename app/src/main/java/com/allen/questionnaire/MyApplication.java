package com.allen.questionnaire;

import android.app.Application;

import com.allen.questionnaire.service.net.CommonRequest;

/**
 * 项目的Application
 *
 * @author Renjy
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络请求
        CommonRequest commonRequest = CommonRequest.getInstance();
        commonRequest.init(this, "http://127.0.0.1:8089/questionnaire");
    }
}
