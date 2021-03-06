package com.allen.questionnaire.service.model;


import com.allen.questionnaire.service.datatrasfer.WinnerResponse;

import java.io.Serializable;

public class Resp implements Serializable, WinnerResponse {
    private static final long serialVersionUID = 1L;
    private Integer statusCode;
    private String reason;


    public Resp() {
    }

    public Resp(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Resp(Integer statusCode, String reason) {
        this.statusCode = statusCode;
        this.reason = reason;
    }

    public boolean OK() {
        return 200 == statusCode;
    }

    public Integer getStatus() {
        return this.statusCode;
    }

    public void setStatus(Integer status) {
        this.statusCode = status;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}