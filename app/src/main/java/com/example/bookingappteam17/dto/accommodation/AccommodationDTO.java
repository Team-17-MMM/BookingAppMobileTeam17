package com.example.bookingappteam17.dto.accommodation;


import com.example.bookingappteam17.enums.AccommodationType;
import com.example.bookingappteam17.enums.Amenity;
import com.example.bookingappteam17.model.Accommodation;
import com.example.bookingappteam17.model.AvailabilityPeriod;
import com.example.bookingappteam17.model.Location;
import com.example.bookingappteam17.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AccommodationDTO {
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

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
  
    public void setAmenities(HashSet<Amenity> amenities) {
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

    public void setAvailabilityPeriods(ArrayList<AvailabilityPeriodDTO> availabilityPeriodsList) {
        this.availabilityPeriods = new HashSet<>(availabilityPeriodsList);
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

    public AccommodationDTO() {}

    public AccommodationDTO(Long accommodationID, String image, Boolean approved, String name, String description, Location location, Set<Amenity> amenities, CapacityDTO capacity, Set<AccommodationType> accommodationType, User owner, Set<AvailabilityPeriodDTO> availabilityPeriods, double price, String confirmationType) {

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

    public AccommodationDTO(Accommodation accommodation) {
        this.accommodationID = accommodation.getAccommodationID();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.location = accommodation.getLocation();
        this.amenities = accommodation.getAmenities();
        this.capacity = new CapacityDTO(accommodation.getCapacity());
        this.accommodationType = accommodation.getAccommodationType();
        this.owner = accommodation.getOwner();
        this.availabilityPeriods = new HashSet<>();
        for (AvailabilityPeriod availabilityPeriod : accommodation.getAvailabilityPeriods()) {
            this.availabilityPeriods.add(new AvailabilityPeriodDTO(availabilityPeriod));
        }
        this.price = accommodation.getPrice();
        this.confirmationType = accommodation.getConfirmationType();
        this.image = accommodation.getImage();
        this.approved = accommodation.getApproved();
    }

    public void copyValues(AccommodationDTO accommodation) {
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.location = accommodation.getLocation();
        this.amenities = accommodation.getAmenities();
        this.capacity = accommodation.getCapacity();
        this.accommodationType = accommodation.getAccommodationType();
        this.owner = accommodation.getOwner();
        this.availabilityPeriods = accommodation.getAvailabilityPeriods();
        this.price = accommodation.getPrice();
        this.confirmationType = accommodation.getConfirmationType();
        this.image = accommodation.getImage();
        this.approved = accommodation.getApproved();
    }

    @Override
    public String toString() {
        return "AccommodationDTO{" +
                "accommodationID=" + accommodationID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", amenities=" + amenities +
                ", capacity=" + capacity +
                ", accommodationType=" + accommodationType +
                ", owner=" + owner +
                ", availabilityPeriods=" + availabilityPeriods +
                ", price=" + price +
                ", confirmationType='" + confirmationType + '\'' +
                ", approved=" + approved +
                '}';
    }
}
