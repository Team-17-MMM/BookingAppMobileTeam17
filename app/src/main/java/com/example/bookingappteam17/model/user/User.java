package com.example.bookingappteam17.model.user;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.enums.user.UserRoleType;

import java.io.Serializable;

public class User implements Parcelable, Serializable {
    private Long userID;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String address;
    private String phone;
    private UserRoleType userRole;

    public User() {}

    public User(Long userID, String username, String password, String name, String lastname, String address, String phone, UserRoleType userRole) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.userRole = userRole;
    }

    public User(String username, String password, String name, String lastname, String address, String phone, UserRoleType userRole) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.userRole = userRole;
    }

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            userID = null;
        } else {
            userID = in.readLong();
        }
        username = in.readString();
        password = in.readString();
        name = in.readString();
        lastname = in.readString();
        address = in.readString();
        phone = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserRoleType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleType userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", userRole=" + userRole +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (userID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(userID);
        }
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeString(name);
        parcel.writeString(lastname);
        parcel.writeString(address);
        parcel.writeString(phone);
    }
}
