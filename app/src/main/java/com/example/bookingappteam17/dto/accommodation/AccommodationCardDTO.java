package com.example.bookingappteam17.dto.accommodation;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.model.Accommodation;

public class AccommodationCardDTO implements Parcelable {
    private Long accommodationID;
    private String name;
    private String description;
    private String image;

    public AccommodationCardDTO() {}
    public AccommodationCardDTO(Long accommodationID, String name, String description, String image) {
        this.accommodationID = accommodationID;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public AccommodationCardDTO(Accommodation accommodation) {
        this.accommodationID = accommodation.getAccommodationID();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.image = accommodation.getImage();
    }

    protected AccommodationCardDTO(Parcel in) {
        if (in.readByte() == 0) {
            accommodationID = null;
        } else {
            accommodationID = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        image = in.readString();
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
        if (accommodationID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(accommodationID);
        }
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(image);
    }
}
