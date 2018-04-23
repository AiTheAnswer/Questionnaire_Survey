package com.allen.questionnaire.service.model;

import java.io.Serializable;

/**
 *  问卷的实体类
 *
 *  @author  Renjy
 */

public class Questionnaire implements Serializable{
    //问卷id
    private String id;
    //问卷名称
    private String questionnaireName;
    //问卷类别id
    private int categoryId;
    //问卷的问题个数
    private Integer questionNumber;
    //当前用户是否使用
    private boolean isUse;
    //当前问卷被使用个次数
    private Integer useNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionnaireName() {
        return questionnaireName;
    }

    public void setQuestionnaireName(String questionnaireName) {
        this.questionnaireName = questionnaireName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public boolean isUse() {
        return isUse;
    }

    public void setUse(boolean use) {
        isUse = use;
    }

    public Integer getUseNumber() {
        return useNumber;
    }

    public void setUseNumber(Integer useNumber) {
        this.useNumber = useNumber;
    }
}
