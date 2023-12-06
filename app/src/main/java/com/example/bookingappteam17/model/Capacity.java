package com.example.bookingappteam17.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Capacity implements Parcelable {
    private int minOccupancy;
    private int maxOccupancy;

    public Capacity(int minOccupancy, int maxOccupancy) {
        this.minOccupancy = minOccupancy;
        this.maxOccupancy = maxOccupancy;
    }

    public Capacity(){}

    public int getMinOccupancy() {
        return minOccupancy;
    }

    public void setMinOccupancy(int minOccupancy) {
        this.minOccupancy = minOccupancy;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }

    protected Capacity(Parcel in) {
        minOccupancy = in.readInt();
        maxOccupancy = in.readInt();
    }

    public static final Creator<Capacity> CREATOR = new Creator<Capacity>() {
        @Override
        public Capacity createFromParcel(Parcel in) {
            return new Capacity(in);
        }

        @Override
        public Capacity[] newArray(int size) {
            return new Capacity[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(minOccupancy);
        dest.writeInt(maxOccupancy);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
