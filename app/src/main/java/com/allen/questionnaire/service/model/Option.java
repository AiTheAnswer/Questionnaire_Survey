package com.allen.questionnaire.service.model;

/**
 * 选项的实体类
 *
 * @author Renjy
 */

public class Option {
    private Long id;
    private String optionDes;
    private boolean isSelect;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionDes() {
        return optionDes;
    }

    public void setOptionDes(String optionDes) {
        this.optionDes = optionDes;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
