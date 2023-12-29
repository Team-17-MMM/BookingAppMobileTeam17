package com.example.bookingappteam17.dto.user;

import com.example.bookingappteam17.enums.user.UserRoleType;

import java.io.Serializable;

public class UserRegistrationDTO implements Serializable {

    private Long userID;
    private String username;
    private String password;
    private String name;
    private String lastname;
    private String address;
    private String phone;
    private boolean enabled;
    private UserRoleType userRole;

    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(Long userID, String username, String password, String name, String lastname, String address, String phone, UserRoleType userRole, boolean enabled) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.phone = phone;
        this.userRole = userRole;
        this.enabled = enabled;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public UserRoleType getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleType userRole) {
        this.userRole = userRole;
    }
}
