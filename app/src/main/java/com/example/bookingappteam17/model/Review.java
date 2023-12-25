package com.example.bookingappteam17.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Review implements Parcelable {
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

    protected Review(Parcel in) {
        grade = in.readInt();
        comment = in.readString();
        userID = in.readInt();
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(grade);
        dest.writeString(comment);
        dest.writeInt(userID);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
