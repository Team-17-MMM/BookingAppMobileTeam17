package com.example.bookingappteam17.dto.accommodation;


import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import java.io.Serializable;

public class AccommodationCardRDTO implements Parcelable, Serializable {
    private Long accommodationID;
    private String name;
    private String description;
    private String image;

    public AccommodationCardRDTO() {}
    public AccommodationCardRDTO(Long accommodationID, String name, String description, String image) {
        this.accommodationID = accommodationID;
        this.name = name;
        this.description = description;
        this.image = image;
    }


    public static final Creator<AccommodationCardDTO> CREATOR = new Creator<AccommodationCardDTO>() {
        @Override
        public AccommodationCardDTO createFromParcel(Parcel in) {
            return new AccommodationCardDTO(in);
        }

        @Override
        public AccommodationCardDTO[] newArray(int size) {
            return new AccommodationCardDTO[size];
        }
    };

    public Long getAccommodationID() {
        return accommodationID;
    }

    public void setAccommodationID(Long accommodationID) {
        this.accommodationID = accommodationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
    }

}