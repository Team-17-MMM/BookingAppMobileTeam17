package com.example.bookingappteam17.dto.accommodation;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.model.accommodation.Accommodation;

import java.io.Serializable;

public class AccommodationCardDTO implements Parcelable, Serializable {
    private Long accommodationID;
    private String name;
    private String description;
    private Bitmap image;

    public AccommodationCardDTO() {}
    public AccommodationCardDTO(Long accommodationID, String name, String description, Bitmap image) {
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

    public AccommodationCardDTO(AccommodationCardRDTO accommodationCardRDTO) {
        this.accommodationID = accommodationCardRDTO.getAccommodationID();
        this.name = accommodationCardRDTO.getName();
        this.description = accommodationCardRDTO.getDescription();

        // Assuming 'accommodationCardRDTO.getImage()' returns the Base64-encoded string
        String base64Image = accommodationCardRDTO.getImage();

        // Decode the Base64 string into a byte array
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);

        // Convert the byte array to a Bitmap
        this.image = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    protected AccommodationCardDTO(Parcel in) {
        if (in.readByte() == 0) {
            accommodationID = null;
        } else {
            accommodationID = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
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
        parcel.writeParcelable(image, i);
    }

}