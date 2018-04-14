package com.allen.questionnaire.service.model;

/**
 * 类别实体类
 *
 * @author Renjy
 */

public class Category {
    //id
    private int id;
    //类别名称
    private String categoryName;
    //父类别Id
    private String parentId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
