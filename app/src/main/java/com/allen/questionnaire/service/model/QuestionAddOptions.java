package com.allen.questionnaire.service.model;

import java.util.List;

/**
 * 问题和选项集合的实体类
 * @author Renjy
 */

public class QuestionAddOptions {
    private Question question;
    private List<Option> optionList;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<Option> getOptionList() {
        return optionList;
    }

    public void setOptionList(List<Option> optionList) {
        this.optionList = optionList;
    }
}
