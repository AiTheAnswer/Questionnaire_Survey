package com.allen.questionnaire.service.model;

import java.io.Serializable;

/**
 * 选项被选择的个数
 *
 * @author Renjy
 */
public class OptionStatistics implements Serializable {
    private Option option;
    private Long count;

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}