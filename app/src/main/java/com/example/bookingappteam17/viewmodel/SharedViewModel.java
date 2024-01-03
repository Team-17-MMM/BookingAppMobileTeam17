package com.example.bookingappteam17.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.dto.user.UserInfoDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;

import java.util.ArrayList;
import java.util.List;

public class SharedViewModel extends ViewModel implements Parcelable {
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

    public void addAccommodationCard(AccommodationCardDTO accommodationCardDTO) { this.accommodationCards.add(accommodationCardDTO); }

    public void clearAccommodations(){this.accommodationCards.clear();}
    public SharedViewModel() {
    }
    protected SharedViewModel(Parcel in) {
        userInfoDTO = in.readParcelable(UserInfoDTO.class.getClassLoader());
        accommodationCards = in.createTypedArrayList(AccommodationCardDTO.CREATOR);
    }
    public static final Creator<SharedViewModel> CREATOR = new Creator<SharedViewModel>() {
        @Override
        public SharedViewModel createFromParcel(Parcel in) {
            return new SharedViewModel(in);
        }

        @Override
        public SharedViewModel[] newArray(int size) {
            return new SharedViewModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userInfoDTO, flags);
        dest.writeTypedList(accommodationCards);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
