package com.allen.questionnaire.service.model;

import java.util.List;

public class RespQueRecordList extends Resp {
    private List<RespQueRecord> object;

    public List<RespQueRecord> getRespQueRecordList() {
        return object;
    }

    public void setRespQueRecordList(List<RespQueRecord> respQueRecordList) {
        this.object = respQueRecordList;
    }
}
