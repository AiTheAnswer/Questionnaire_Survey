package com.allen.questionnaire.service.model;

import java.util.ArrayList;
import java.util.List;

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
    //用户该问题的选项集合
    private List<Option> selectOptions;

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

    public List<Option> getSelectOptions() {
        if(null == selectOptions){
            selectOptions = new ArrayList<>();
        }
        return selectOptions;
    }

    public void setSelectOptions(List<Option> selectOptions) {
        this.selectOptions = selectOptions;
    }
}
