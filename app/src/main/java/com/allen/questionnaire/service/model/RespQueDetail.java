package com.allen.questionnaire.service.model;

import java.util.List;

/**
 * 问卷详情的实体类（问题和选项）
 * @author  Renjy
 */


public class RespQueDetail extends Resp {
    private List<QuestionAddOptions> quesDetail;

    public List<QuestionAddOptions> getQuesDetail() {
        return quesDetail;
    }

    public void setQuesDetail(List<QuestionAddOptions> quesDetail) {
        this.quesDetail = quesDetail;
    }
}
