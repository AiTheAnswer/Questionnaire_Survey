package com.allen.questionnaire.service.httputil;


/**
 * Created by Renjy on 2018/1/30.
 * 请求的一些配置（超时时间、是否缓存）
 */

public class Config {
    public long connectionTimeOut = 30000;
    public long readTimeOut = 30000;

    public Config(long connectionTimeOut, long readTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
        this.readTimeOut = readTimeOut;
    }
}
