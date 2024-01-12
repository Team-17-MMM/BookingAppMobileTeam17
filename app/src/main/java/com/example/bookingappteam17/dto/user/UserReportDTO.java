package com.example.bookingappteam17.dto.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class UserReportDTO implements Parcelable {
    private Long id;
    private Long userID;
    private String reportedUser;
    private String description;
    private boolean banned;

    public UserReportDTO(Long id, Long userID, String reportedUser, String description, boolean banned) {
        this.id = id;
        this.userID = userID;
        this.reportedUser = reportedUser;
        this.description = description;
        this.banned = banned;
    }

    public UserReportDTO(){}

    protected UserReportDTO(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        if (in.readByte() == 0) {
            userID = null;
        } else {
            userID = in.readLong();
        }
        reportedUser = in.readString();
        description = in.readString();
        banned = in.readByte() != 0;
    }

    public static final Creator<UserReportDTO> CREATOR = new Creator<UserReportDTO>() {
        @Override
        public UserReportDTO createFromParcel(Parcel in) {
            return new UserReportDTO(in);
        }

        @Override
        public UserReportDTO[] newArray(int size) {
            return new UserReportDTO[size];
        }
    };

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(String reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        if (userID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(userID);
        }
        dest.writeString(reportedUser);
        dest.writeString(description);
        dest.writeByte((byte) (banned ? 1 : 0));
    }
}
