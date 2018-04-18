package com.allen.questionnaire.service.model;

/**
 * 问题实体类
 *
 * @author Renjy
 */

public class Question {
    private String id;//主键id
    private String questionDes;//问题描述
    private Integer type; //是否可以多选 0： 不可以 1：可以
    private String optionIds;
    private String questionnaireId;//问卷Id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionDes() {
        return questionDes;
    }

    public void setQuestionDes(String questionDes) {
        this.questionDes = questionDes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(String optionIds) {
        this.optionIds = optionIds;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }
}
