package com.example.bookingappteam17.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.dto.UserInfoDTO;

public class SharedViewModel extends ViewModel {
    private UserInfoDTO userInfoDTO = new UserInfoDTO();

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }
    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }
}
