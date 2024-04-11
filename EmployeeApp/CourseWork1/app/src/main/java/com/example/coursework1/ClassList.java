package com.example.coursework1;

public class ClassList {
    String teacherName, date, comments;
    byte[] image;
    public ClassList(String teacherName, String date, String comments, byte[] image) {
        this.teacherName = teacherName;
        this.date = date;
        this.comments = comments;
        this.image = image;
    }
    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
