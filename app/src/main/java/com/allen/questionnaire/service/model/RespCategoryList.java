package com.allen.questionnaire.service.model;

import java.util.ArrayList;

/**
 * 问卷类别集合
 * @author Renjy
 */

public class RespCategoryList extends Resp {
    private ArrayList<Category> object;

    public ArrayList<Category> getObject() {
        return object;
    }

    public void setObject(ArrayList<Category> object) {
        this.object = object;
    }
}
