package com.example.bookingappteam17.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.enums.UserRoleType;

import java.io.Serializable;

public class UserInfoDTO implements Serializable, Parcelable {
    private Long userID;
    private String username;
    private String name;
    private String lastname;
    private String address;
    private String phone;

    public UserInfoDTO() {
    }

    public UserInfoDTO(Long userID, String username, String name, String lastname, String address, String phone) {
        this.userID = userID;
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    protected UserInfoDTO(Parcel in) {
        if (in.readByte() == 0) {
            userID = null;
        } else {
            userID = in.readLong();
        }
        username = in.readString();
        name = in.readString();
        lastname = in.readString();
        address = in.readString();
        phone = in.readString();
    }

    public static final Creator<UserInfoDTO> CREATOR = new Creator<UserInfoDTO>() {
        @Override
        public UserInfoDTO createFromParcel(Parcel in) {
            return new UserInfoDTO(in);
        }

        @Override
        public UserInfoDTO[] newArray(int size) {
            return new UserInfoDTO[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (userID == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(userID);
        }
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(lastname);
        dest.writeString(address);
        dest.writeString(phone);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
