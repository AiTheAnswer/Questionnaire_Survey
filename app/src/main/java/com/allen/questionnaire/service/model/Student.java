package com.allen.questionnaire.service.model;

/**
 * Created by Allen on 2018/4/12.
 */

public class Student {
    /**
     * id : 4028308262a469f10162a46a0f2c0000
     * studentId : 201495114001
     * idNumber : 612826199401016541
     * name : 鲁班
     * sex : 0
     * grade : 四年级
     * profession : 软件工程
     */


    private String id;
    private String studentId;
    private String idNumber;
    private String name;
    private int sex;
    private String grade;
    private String profession;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }
}
