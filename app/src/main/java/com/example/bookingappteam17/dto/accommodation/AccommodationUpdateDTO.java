package com.example.bookingappteam17.dto.accommodation;

import com.example.bookingappteam17.enums.accommodation.AccommodationType;
import com.example.bookingappteam17.enums.accommodation.Amenity;
import com.example.bookingappteam17.model.accommodation.Location;
import com.example.bookingappteam17.model.user.User;

import java.util.HashSet;
import java.util.Set;

public class AccommodationUpdateDTO {
    private Long accommodationID;
    private String name;
    private String description;
    private Location location;
    private
    Set<Amenity> amenities = new HashSet<>();
    private CapacityDTO capacity;
    private Set<AccommodationType> accommodationType = new HashSet<>();;
    private User owner;
    private Set<AvailabilityPeriodDTO> availabilityPeriods = new HashSet<>();
    private double price;
    private String confirmationType;
    private String image;
    private Boolean approved;

    public AccommodationUpdateDTO() {}

    public AccommodationUpdateDTO(Long accommodationID, String name, String description, Location location, Set<Amenity> amenities, CapacityDTO capacity, Set<AccommodationType> accommodationType, User owner, Set<AvailabilityPeriodDTO> availabilityPeriods, double price, String confirmationType, String image, Boolean approved) {
        this.accommodationID = accommodationID;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.capacity = capacity;
        this.accommodationType = accommodationType;
        this.owner = owner;
        this.availabilityPeriods = availabilityPeriods;
        this.price = price;
        this.confirmationType = confirmationType;
        this.image = image;
        this.approved = approved;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    public CapacityDTO getCapacity() {
        return capacity;
    }

    public void setCapacity(CapacityDTO capacity) {
        this.capacity = capacity;
    }

    public Set<AccommodationType> getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(Set<AccommodationType> accommodationType) {
        this.accommodationType = accommodationType;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<AvailabilityPeriodDTO> getAvailabilityPeriods() {
        return availabilityPeriods;
    }

    public void setAvailabilityPeriods(Set<AvailabilityPeriodDTO> availabilityPeriods) {
        this.availabilityPeriods = availabilityPeriods;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getConfirmationType() {
        return confirmationType;
    }

    public void setConfirmationType(String confirmationType) {
        this.confirmationType = confirmationType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
