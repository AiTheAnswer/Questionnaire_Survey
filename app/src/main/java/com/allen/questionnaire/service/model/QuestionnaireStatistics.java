package com.allen.questionnaire.service.model;

import java.util.List;

/**
 * 问卷中某个问题的统计数据
 *
 * @author Renjy
 */
public class QuestionnaireStatistics {
    private Question question;
    private List<OptionStatistics> optionStatistics;

    public QuestionnaireStatistics() {

    }

    public QuestionnaireStatistics(Question question) {
        this.question = question;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public List<OptionStatistics> getOptionStatistics() {
        return optionStatistics;
    }

    public void setOptionStatistics(List<OptionStatistics> optionStatistics) {
        this.optionStatistics = optionStatistics;
    }
}
