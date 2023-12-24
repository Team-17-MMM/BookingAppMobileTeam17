package com.example.bookingappteam17.viewmodel;

import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.dto.UserInfoDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel {
    private UserInfoDTO userInfoDTO = new UserInfoDTO();
    private List<AccommodationCardDTO> accommodationCards = new ArrayList<>();

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }
    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    public List<AccommodationCardDTO> getAccommodationCards() { return accommodationCards; }

    public void setAccommodationCards(List<AccommodationCardDTO> accommodationCards) { this.accommodationCards = accommodationCards; }
}
