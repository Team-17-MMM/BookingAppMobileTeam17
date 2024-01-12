package com.example.bookingappteam17.model.review;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class    ReportedReviewCardDTO implements Parcelable {
    private Long reportedAccommodationReviewDetailsID;
    private Long reviewID;
    private String comment;
    private Long grade;
    private String accommodationName;
    private String username;
    private String hostPath;
    
    public ReportedReviewCardDTO() {
    }
    
    public ReportedReviewCardDTO(Long reportedAccommodationReviewDetailsID, Long reviewID, String comment, Long grade, String accommodationName, String username, String hostPath) {
        this.reportedAccommodationReviewDetailsID = reportedAccommodationReviewDetailsID;
        this.reviewID = reviewID;
        this.comment = comment;
        this.grade = grade;
        this.accommodationName = accommodationName;
        this.username = username;
        this.hostPath = hostPath;
    }

    public ReportedReviewCardDTO(ReportedReviewCardDTO reportedReviewCardDTO) {
        this.reportedAccommodationReviewDetailsID = reportedReviewCardDTO.getReportedAccommodationReviewDetailsID();
        this.reviewID = reportedReviewCardDTO.getReviewID();
        this.comment = reportedReviewCardDTO.getComment();
        this.grade = reportedReviewCardDTO.getGrade();
        this.accommodationName = reportedReviewCardDTO.getAccommodationName();
        this.username = reportedReviewCardDTO.getUsername();
        this.hostPath = reportedReviewCardDTO.getHostPath();
    }

    protected ReportedReviewCardDTO(Parcel in) {
        if (in.readByte() == 0) {
            reportedAccommodationReviewDetailsID = null;
        } else {
            reportedAccommodationReviewDetailsID = in.readLong();
        }
        if (in.readByte() == 0) {
            reviewID = null;
        } else {
            reviewID = in.readLong();
        }
        comment = in.readString();
        if (in.readByte() == 0) {
            grade = null;
        } else {
            grade = in.readLong();
        }
        accommodationName = in.readString();
        username = in.readString();
        hostPath = in.readString();
    }

    public static final Creator<ReportedReviewCardDTO> CREATOR = new Creator<ReportedReviewCardDTO>() {
        @Override
        public ReportedReviewCardDTO createFromParcel(Parcel in) {
            return new ReportedReviewCardDTO(in);
        }

        @Override
        public ReportedReviewCardDTO[] newArray(int size) {
            return new ReportedReviewCardDTO[size];
        }
    };

    public Long getReportedAccommodationReviewDetailsID() {
        return reportedAccommodationReviewDetailsID;
    }

    public void setReportedAccommodationReviewDetailsID(Long reportedAccommodationReviewDetailsID) {
        this.reportedAccommodationReviewDetailsID = reportedAccommodationReviewDetailsID;
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getGrade() {
        return grade;
    }

    public void setGrade(Long grade) {
        this.grade = grade;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHostPath() {
        return hostPath;
    }

    public void setHostPath(String hostPath) {
        this.hostPath = hostPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (reportedAccommodationReviewDetailsID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(reportedAccommodationReviewDetailsID);
        }
        if (reviewID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(reviewID);
        }
        parcel.writeString(comment);
        if (grade == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(grade);
        }
        parcel.writeString(accommodationName);
        parcel.writeString(username);
        parcel.writeString(hostPath);
    }
}
