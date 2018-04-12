package com.allen.questionnaire.service.cache.db.bean;

import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;


/**
 * Created by Renjy on 2018/3/13.
 * 菜单列表实体类
 */
@Entity
public class CacheEntity  {
    @Id(autoincrement = true)
    private Long id;
    @NonNull
    private String host;//url地址
    @NonNull
    private String action;//接口名称
    @NonNull
    private String params;//该接口的参数，参数拼接后用MD5转换
    @NonNull
    private String result;//该接口的返回结果的json字符串
    @NonNull
    private String createTime;//创建时间

    public CacheEntity(@NonNull String host, @NonNull String action, @NonNull String params,
                       @NonNull String result, @NonNull String createTime) {
        this.host = host;
        this.action = action;
        this.params = params;
        this.result = result;
        this.createTime = createTime;
    }

    @Generated(hash = 1929818076)
    public CacheEntity(Long id, @NonNull String host, @NonNull String action,
                       @NonNull String params, @NonNull String result,
                       @NonNull String createTime) {
        this.id = id;
        this.host = host;
        this.action = action;
        this.params = params;
        this.result = result;
        this.createTime = createTime;
    }

    @Generated(hash = 1391258017)
    public CacheEntity() {
    }

    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResult() {
        return this.result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
