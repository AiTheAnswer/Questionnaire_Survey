package com.allen.questionnaire;

import android.app.Application;

import com.allen.questionnaire.service.net.CommonRequest;
import com.allen.questionnaire.utils.SharedPreferenceUtils;

import static com.allen.questionnaire.utils.Constant.TOKEN;

/**
 * 项目的Application
 *
 * @author Renjy
 */
public class MyApplication extends Application {
    private SharedPreferenceUtils preferenceUtils;


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络请求
        CommonRequest commonRequest = CommonRequest.getInstance();
        commonRequest.init(this, "http://192.168.2.121:8089/questionnaire");
    }

    /**
     * 获取Token
     * @return token
     */
    public String getToken(){
        preferenceUtils = SharedPreferenceUtils.getInstance(this);
        return preferenceUtils.getPreferenceString(TOKEN,"");
    }
}
