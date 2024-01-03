package com.example.bookingappteam17.dto.accommodation;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.model.accommodation.Capacity;

import java.io.Serializable;

public class CapacityDTO implements Serializable, Parcelable {
    private int minGuests;
    private int maxGuests;

    public CapacityDTO() {}

    public CapacityDTO(int minGuests, int maxGuests) {
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
    }

    public CapacityDTO(Capacity capacity) {
        this.minGuests = capacity.getMinGuests();
        this.maxGuests = capacity.getMaxGuests();
    }

    protected CapacityDTO(Parcel in) {
        minGuests = in.readInt();
        maxGuests = in.readInt();
    }

    public static final Creator<CapacityDTO> CREATOR = new Creator<CapacityDTO>() {
        @Override
        public CapacityDTO createFromParcel(Parcel in) {
            return new CapacityDTO(in);
        }

        @Override
        public CapacityDTO[] newArray(int size) {
            return new CapacityDTO[size];
        }
    };

    public int getMinGuests() {
        return minGuests;
    }
    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }
    public int getMaxGuests() {
        return maxGuests;
    }
    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    @Override
    public String toString() {
        return "CapacityDTO{" +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(minGuests);
        parcel.writeInt(maxGuests);
    }
}
