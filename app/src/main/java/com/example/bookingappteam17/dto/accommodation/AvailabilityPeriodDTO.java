package com.example.bookingappteam17.dto.accommodation;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.model.accommodation.AvailabilityPeriod;

import java.io.Serializable;

public class AvailabilityPeriodDTO implements Serializable, Parcelable {
    private String date;
    private Long price;

    public AvailabilityPeriodDTO() {}

    public AvailabilityPeriodDTO(String date, Long price) {
        this.date = date;
        this.price = price;
    }

    public AvailabilityPeriodDTO(AvailabilityPeriod availabilityPeriod) {
        this.date = availabilityPeriod.getDate().toString();
        this.price = availabilityPeriod.getPrice();
    }

    protected AvailabilityPeriodDTO(Parcel in) {
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
    }

    public static final Creator<AvailabilityPeriodDTO> CREATOR = new Creator<AvailabilityPeriodDTO>() {
        @Override
        public AvailabilityPeriodDTO createFromParcel(Parcel in) {
            return new AvailabilityPeriodDTO(in);
        }

        @Override
        public AvailabilityPeriodDTO[] newArray(int size) {
            return new AvailabilityPeriodDTO[size];
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AvailabilityPeriodDTO{" +
                ", startDate=" + date +
                ", endDate=" + price +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(price);
        }
    }
}
