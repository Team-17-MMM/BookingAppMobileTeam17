package com.example.bookingappteam17.model.accommodation;

import com.example.bookingappteam17.enums.accommodation.Amenities;
import com.example.bookingappteam17.enums.accommodation.AccommodationType;

import java.time.LocalDate;
import java.util.List;

public class FilterData {
    private String city;
    private int minPrice;
    private int maxPrice;
    private int occupancy;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Amenities> amenities;
    private AccommodationType AccommodationType;

    public FilterData(String city, int minPrice, int maxPrice, int occupancy, LocalDate startDate, LocalDate endDate, List<Amenities> amenities, AccommodationType AccommodationType) {
        this.city = city;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.occupancy = occupancy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amenities = amenities;
        this.AccommodationType = AccommodationType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenities> amenities) {
        this.amenities = amenities;
    }

    public AccommodationType getAccommodationType() {
        return AccommodationType;
    }

    public void setAccommodationType(AccommodationType AccommodationType) {
        this.AccommodationType = AccommodationType;
    }
}
