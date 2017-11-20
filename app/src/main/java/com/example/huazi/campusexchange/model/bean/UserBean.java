package com.example.huazi.campusexchange.model.bean;

/**
 * Created by huazi on 2017/11/7.
 */

public class UserBean {
    private int id;
    private String userName;                            //用户名
    private String userPsw;                             //密码
    private String nickName;                            //昵称
    private String campusName;                          //学校
    private String faculty;                             //院系
    private String major;                               //专业
    private String education;                           //学历
    private String e_mail;                              //邮箱
    private String tel;                                 //电话
    private String enrollment_Year;                     //入学年份

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEnrollment_Year() {
        return enrollment_Year;
    }

    public void setEnrollment_Year(String enrollment_Year) {
        this.enrollment_Year = enrollment_Year;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCampusName() {
        return campusName;
    }

    public void setCampusName(String campusName) {
        this.campusName = campusName;
    }
}
