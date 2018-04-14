package com.allen.questionnaire.service.model;

import java.util.List;

/**
 * 问卷集合
 * @author Renjy
 */

public class RespQuestionnaireList extends Resp{
    private List<Questionnaire> object;

    public List<Questionnaire> getObject() {
        return object;
    }

    public void setObject(List<Questionnaire> object) {
        this.object = object;
    }
}
