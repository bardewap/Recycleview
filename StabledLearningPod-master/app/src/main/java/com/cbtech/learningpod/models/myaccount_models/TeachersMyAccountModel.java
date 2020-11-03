package com.cbtech.learningpod.models.myaccount_models;

import com.cbtech.learningpod.models.CCModel;
import com.cbtech.learningpod.models.ClassInfoModel;
import com.cbtech.learningpod.models.ClassModel;
import com.cbtech.learningpod.models.SubjectInfoModel;
import com.cbtech.learningpod.models.TeacherModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeachersMyAccountModel implements Serializable, Cloneable {

    @SerializedName("teacherInfo")
    TeacherModel teacherModel;

    @SerializedName("ccInfo")
    CCModel ccModel;

    @SerializedName(value = "teacherClassInfo", alternate = {"ccClassInfo"})
    List<ClassInfoModel> classInfoModelList;

    @SerializedName(value = "teacherSubjectInfo", alternate = {"ccSubjectInfo"})
    List<SubjectInfoModel> subjectInfoModels;

    private List<String> subjectList;
    private List<String> classList;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public TeachersMyAccountModel(TeacherModel teacherModel) {
        this.teacherModel = teacherModel;
        //subjectList = new ArrayList<>();
    }

    public TeacherModel getTeacherModel() {
        return teacherModel;
    }

    public void setTeacherModel(TeacherModel teacherModel) {
        this.teacherModel = teacherModel;
    }

    public List<ClassInfoModel> getClassInfoModelList() {
        return classInfoModelList;
    }

    public void setClassInfoModelList(List<ClassInfoModel> classInfoModelList) {
        this.classInfoModelList = classInfoModelList;
    }

    public List<SubjectInfoModel> getSubjectInfoModels() {
        return subjectInfoModels;
    }

    public void setSubjectInfoModels(List<SubjectInfoModel> subjectInfoModels) {
        this.subjectInfoModels = subjectInfoModels;
    }

    public List<String> getSubjectList(){
        subjectList = new ArrayList<>();
        if(getSubjectInfoModels() != null) {
            if (!getSubjectInfoModels().isEmpty()) {

                for (int i = 0; i < getSubjectInfoModels().size(); i++) {

                    if (getSubjectInfoModels().get(i).getSubjectModel() != null) {
                        subjectList.add(getSubjectInfoModels().get(i).getSubjectModel().getSubjectName());
                    }
                }
                return subjectList;
            } else {
                return subjectList;
            }
        }else {
            return subjectList;
        }
    }

    public String getSubjectsString() {
        StringBuilder subjects = new StringBuilder();
        if(!getSubjectList().isEmpty()){

            for(String subjectName : getSubjectList()){
                subjects.append(subjectName).append(", ");
            }
            return subjects.substring(0, subjects.length() - 2);

        }else{
            return null;
        }
    }

    public String getClassesString() {
        StringBuilder classes = new StringBuilder();
        if(!getClassList().isEmpty()){

            for(String subjectName : getClassList()){
                classes.append(subjectName).append(", ");
            }
            return classes.substring(0, classes.length() - 2);

        }else{
            return null;
        }
    }

    public List<String> getClassList(){
        classList = new ArrayList<>();
        if(getClassInfoModelList() != null) {
            if (!getClassInfoModelList().isEmpty()) {

                for (int i = 0; i < getClassInfoModelList().size(); i++) {

                    if (getClassInfoModelList().get(i).getClassModel() != null) {
                        classList.add(getClassInfoModelList().get(i).getClassModel().getClassDetail());
                    }
                }

                return classList;
            } else {
                return classList;
            }
        }else{
           return classList;
        }
    }

    public CCModel getCcModel() {
        return ccModel;
    }

    public void setCcModel(CCModel ccModel) {
        this.ccModel = ccModel;
    }

    @Override
    public String toString() {
        return "TeachersMyAccountModel{" +
                "teacherModel=" + teacherModel +
                ", ccModel=" + ccModel +
                ", classInfoModelList=" + classInfoModelList +
                ", subjectInfoModels=" + subjectInfoModels +
                ", subjectList=" + subjectList +
                ", classList=" + classList +
                '}';
    }
}
