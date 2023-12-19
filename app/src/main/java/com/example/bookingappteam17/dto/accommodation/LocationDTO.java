package com.example.bookingappteam17.dto.accommodation;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class LocationDTO implements Serializable, Parcelable {

    private String address;

    private String country;

    private Double longitude;

    private Double latitude;

    public LocationDTO(){}

    public LocationDTO(String address, String country, Double latitude, Double longitude){
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public LocationDTO(LocationDTO location){
        this.address = location.getAddress();
        this.country = location.getCountry();
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    protected LocationDTO(Parcel in) {
        address = in.readString();
        country = in.readString();
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
    }

    public static final Creator<LocationDTO> CREATOR = new Creator<LocationDTO>() {
        @Override
        public LocationDTO createFromParcel(Parcel in) {
            return new LocationDTO(in);
        }

        @Override
        public LocationDTO[] newArray(int size) {
            return new LocationDTO[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(address);
        parcel.writeString(country);
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitude);
        }
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitude);
        }
    }
}
