package com.example.bookingappteam17.dto.accommodation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDate;

public class AvailabilityPeriodRangeDTO  implements Serializable, Parcelable {

    private String startDate;

    private String endDate;
    private Long price;

    public AvailabilityPeriodRangeDTO(String startDate, String endDate, Long price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public AvailabilityPeriodRangeDTO(){}

    protected AvailabilityPeriodRangeDTO(Parcel in) {
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readLong();
        }
    }

    public static final Creator<AvailabilityPeriodRangeDTO> CREATOR = new Creator<AvailabilityPeriodRangeDTO>() {
        @Override
        public AvailabilityPeriodRangeDTO createFromParcel(Parcel in) {
            return new AvailabilityPeriodRangeDTO(in);
        }

        @Override
        public AvailabilityPeriodRangeDTO[] newArray(int size) {
            return new AvailabilityPeriodRangeDTO[size];
        }
    };

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(price);
        }
    }
}
