package com.allen.questionnaire.service.model;


import com.allen.questionnaire.service.datatrasfer.WinnerResponse;

/**
 * Created by Allen on 2018/3/12.
 */

public class RespToken implements WinnerResponse {
    /**
     * token : winnereyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ6a3kiLCJhdWQiOiJFeGFtcGxlQXVkaWVuY2UiLCJpYXQiOjE1MjA4NTAyOTMsImV4cCI6MTUyMDg1MjA5M30.Yjyh8fWAZ8Pa8vYqwgnLXtGG9IWzJCI1f304fOD8ENY
     * exp : 1520852093.20015
     * status : success
     * reason : null
     */

    private String token;
    private String exp;
    private String status;
    private String reason;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
