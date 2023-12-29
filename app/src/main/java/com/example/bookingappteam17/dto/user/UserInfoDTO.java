package com.example.bookingappteam17.dto.user;

import java.io.Serializable;

public class UserInfoDTO implements Serializable {
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

}
