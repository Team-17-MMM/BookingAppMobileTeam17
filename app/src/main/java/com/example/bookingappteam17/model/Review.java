package com.example.bookingappteam17.model;

public class Review {
    private int grade;
    private String comment;
    private int userID;

    public Review(int grade, String comment, int userID) {
        this.grade = grade;
        this.comment = comment;
        this.userID = userID;
    }

    public Review(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
