package com.allen.questionnaire.service.model;

/**
 *  问题记录实体类
 */
public class RespQueRecord extends Resp {
    private String id;
    private String questionId;
    private String optionIds;
    private String recordId;

    public RespQueRecord(String id, String userId, String questionId, String optionIds, String recordId) {
        this.id = id;
        this.questionId = questionId;
        this.optionIds = optionIds;
        this.recordId = recordId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(String optionIds) {
        this.optionIds = optionIds;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}
