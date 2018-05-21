package com.allen.questionnaire.service.model;

import java.util.List;

/**
 * 所有学生列表的实体类
 *
 * @author Renjy
 */
public class RespStudents extends Resp {
    private List<Student> object;


    public List<Student> getObject() {
        return object;
    }

    public void setObject(List<Student> object) {
        this.object = object;
    }
}
