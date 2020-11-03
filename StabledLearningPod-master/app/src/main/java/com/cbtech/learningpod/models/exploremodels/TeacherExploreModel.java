package com.cbtech.learningpod.models.exploremodels;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.cbtech.learningpod.BR;
import com.cbtech.learningpod.Constants.StringConstants;
import com.cbtech.learningpod.R;
import com.cbtech.learningpod.models.ClassInfoModel;
import com.cbtech.learningpod.models.ClassModel;
import com.cbtech.learningpod.models.SubjectModel;
import com.cbtech.learningpod.views.AddTeacherView.UpdateTeacherFragment;
import com.cbtech.learningpod.views.MyCourseView.UpdateCourseFragment;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TeacherExploreModel  extends BaseObservable implements Serializable  {

    @SerializedName("email")
    private String email ;

    @SerializedName("teacherId")
    private String teacherID ;

    @SerializedName("experience")
    private String experience ;

    @SerializedName("lpExpeience")
    private String lpExperience;

    @SerializedName("qualifications")
    private String qualifications ;

    @SerializedName("address1")
    private String address1;

    @SerializedName("address2")
    private String address2 ;

    @SerializedName("teacherName")
    private String teacherName ;

    @SerializedName("creationDate")
    private String creationDate;

    @SerializedName("description")
    private String description;

    @SerializedName("pincode")
    private String pinCode;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("picturePath")
    private String picturePath;

    @SerializedName("subjectCount")
    private int subjectCount;

    @SerializedName("classCount")
    private int classCount;

    @SerializedName("cumulativeRating")
    private float cumulativeRating;

    @SerializedName("phoneNo")
    private String phoneNo;

    @SerializedName("subjectInfo")
    private List<SubjectModel> subjectModelList;
    private SubjectModel subjectModel;

    @SerializedName("classInfo")
    private List<ClassModel> classModelList;
    private ClassModel classModel;

    @SerializedName("isRestrictedAccess")
    private String isRestrictedAccess;

    @SerializedName("teacherSkills")
    private String teacherSkills;



    private TeacherExploreModel teacherExploreModel;
    FragmentManager fragmentManager;


    public List<SubjectModel> getSubjectModelList() {
        return subjectModelList;
    }

    public void setSubjectModelList(List<SubjectModel> subjectModelList) {
        this.subjectModelList = subjectModelList;
    }

    public String getAllSubjects(){
        if(!getSubjectModelList().isEmpty()) {
            StringBuilder allSubjectsString = new StringBuilder();
            for (int i = 0; i < getSubjectModelList().size(); i++) {
                allSubjectsString.append(getSubjectModelList().get(i).getSubjectName()).append(", ");
            }
            return allSubjectsString.substring(0, allSubjectsString.length() - 2);
        }else{
            return "";
        }
    }




    public List<ClassModel> getClassModelList() {
        return classModelList;
    }

    public void setClassModelList(List<ClassModel> classModelList) {
        this.classModelList = classModelList;
    }



    @Bindable
    public String getAllClasses(){
        if(!getClassModelList().isEmpty()){
            StringBuilder allClasses = new StringBuilder();
            for(ClassModel classModel : getClassModelList()){
                Log.d("ClassInfoModel ", classModel.toString());
                allClasses.append( classModel.getClassDetail() ).append(", ");
            }
            return allClasses.substring(0, allClasses.length() - 2);
        }else {
            return "";
        }
    }

    public String getLpExperience() {
        if(lpExperience == null){
            return "0"+ StringConstants.YEARS;
        }
        return lpExperience + StringConstants.YEARS;
    }


    @Bindable
    public String getFullAddress(){

        StringBuilder addressBuiler = new StringBuilder();

        if(address1 != null && !address1.isEmpty()){
            addressBuiler.append(address1).append(" ");
        }

        if(address2 != null && !address2.isEmpty()){
            addressBuiler.append(address2);
        }

        if(state != null && !state.isEmpty()){
            addressBuiler.append("\n").append(state).append(" ");
        }

        if(city != null && !city.isEmpty()){
            addressBuiler.append(city).append(" ");
        }

        if(pinCode != null && !pinCode.isEmpty()){
            addressBuiler.append("\n").append("Pincode ").append(pinCode);
        }
        return  addressBuiler.toString();
    }

    public void setLpExperience(String lpExperience) {
        this.lpExperience = lpExperience;
    }



    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);

    }



    @Bindable
    public String getTeacherSkills() {
        return teacherSkills;
    }

    public void setTeacherSkills(String teacherSkills) {
        this.teacherSkills = teacherSkills;
        notifyPropertyChanged(BR.teacherSkills);

    }




    @Bindable
    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
        notifyPropertyChanged(BR.teacherID);

    }



    @Bindable
    public String getExperience() {
        if(experience == null){
           return "0" + StringConstants.YEARS;
        }
        return experience + StringConstants.YEARS;
    }

    public void setExperience(String experience) {
        this.experience = experience;
        notifyPropertyChanged(BR.experience);

    }



    @Bindable
    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
        notifyPropertyChanged(BR.qualifications);

    }



    @Bindable
    public String getAddress1() {
        if(address1 != null && !address1.isEmpty()){
            return address1 + ",";
        }
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
        notifyPropertyChanged(BR.address1);

    }



    @Bindable
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
        notifyPropertyChanged(BR.address2);

    }



    @Bindable
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
        notifyPropertyChanged(BR.teacherName);

    }



    @Bindable
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
        notifyPropertyChanged(BR.creationDate);

    }



    @Bindable
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);

    }



    @Bindable
    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
        notifyPropertyChanged(BR.pinCode);

    }


    @Bindable
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        notifyPropertyChanged(BR.city);

    }


    @Bindable
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifyPropertyChanged(BR.state);

    }



    @Bindable
    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
        notifyPropertyChanged(BR.picturePath);

    }



    @Bindable
    public int getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(int subjectCount) {
        this.subjectCount = subjectCount;
        notifyPropertyChanged(BR.subjectCount);

    }


    @Bindable
    public int getClassCount() {
        return classCount;
    }

    public void setClassCount(int classCount) {
        this.classCount = classCount;
        notifyPropertyChanged(BR.classCount);

    }


    @Bindable
    public float getCumulativeRating() {
        return cumulativeRating;
    }

    public void setCumulativeRating(float cumulativeRating) {
        this.cumulativeRating = cumulativeRating;
        notifyPropertyChanged(BR.cumulativeRating);

    }


    @Bindable
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
        notifyPropertyChanged(BR.phoneNo);

    }


    @Bindable
    public String getIsRestrictedAccess() {
        return isRestrictedAccess;
    }

    public void setIsRestrictedAccess(String isRestrictedAccess) {
        this.isRestrictedAccess = isRestrictedAccess;
        notifyPropertyChanged(BR.isRestrictedAccess);

    }

    @Bindable
    public SubjectModel getSubjectModel() {
        return subjectModel;
    }

    public void setSubjectModel(SubjectModel subjectModel) {
        this.subjectModel = subjectModel;
        notifyPropertyChanged(com.cbtech.learningpod.BR.subjectModel);
    }

    @Bindable
    public ClassModel getClassModel() {
        return classModel;
    }

    public void setClassModel(ClassModel classModel) {
        this.classModel = classModel;
        notifyPropertyChanged(com.cbtech.learningpod.BR.classModel);
    }



    @Override
    public String toString() {
        return "TeacherExploreModel{" +
                "email='" + email + '\'' +
                ", teacherID='" + teacherID + '\'' +
                ", experience='" + experience + '\'' +
                ", lpExperience='" + lpExperience + '\'' +
                ", qualifications='" + qualifications + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", description='" + description + '\'' +
                ", pinCode='" + pinCode + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", subjectCount=" + subjectCount +
                ", classCount=" + classCount +
                ", cumulativeRating=" + cumulativeRating +
                ", phoneNo='" + phoneNo + '\'' +
                ", subjectModelList=" + subjectModelList +
                ", classModelList=" + classModelList +
                ", isRestrictedAccess='" + isRestrictedAccess + '\'' +
                ", subjectModel=" + subjectModel +
                ", classModel=" + classModel +
                '}';
    }





}
