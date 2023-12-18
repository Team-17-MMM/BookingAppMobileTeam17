package com.example.bookingappteam17.dto.accommodation;

public class LocationDTO {

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
}
