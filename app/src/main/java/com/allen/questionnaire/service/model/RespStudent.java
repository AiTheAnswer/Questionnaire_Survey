package com.allen.questionnaire.service.model;

/**
 * 学生实体类
 *
 * @author Renjy
 */
public class RespStudent extends Resp {

    private Student object;

    public Student getObject() {
        return object;
    }

    public void setObject(Student object) {
        this.object = object;
    }
}
