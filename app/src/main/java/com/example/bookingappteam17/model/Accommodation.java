package com.example.bookingappteam17.model;

import android.graphics.Bitmap;

import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.AvailabilityPeriodDTO;
import com.example.bookingappteam17.enums.AccommodationType;
import com.example.bookingappteam17.enums.Amenity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Accommodation implements Serializable {

    public Accommodation() {}

    public Accommodation(Long accommodationID, Boolean approved, Bitmap image, String name, String description, Location location, Set<Amenity> amenities, Capacity capacity, Set<AccommodationType> accommodationType, User owner, Set<AvailabilityPeriod> availabilityPeriods, double price, String confirmationType) {

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

    public Accommodation(AccommodationDTO accommodation) {
        this.accommodationID = accommodation.getAccommodationID();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.location = accommodation.getLocation();
        this.amenities = accommodation.getAmenities();
        this.capacity = new Capacity(accommodation.getCapacity());
        this.accommodationType = accommodation.getAccommodationType();
        this.owner = accommodation.getOwner();
        this.availabilityPeriods = new HashSet<AvailabilityPeriod>();
        for (AvailabilityPeriodDTO availabilityPeriod : accommodation.getAvailabilityPeriods()) {
            this.availabilityPeriods.add(new AvailabilityPeriod(availabilityPeriod));
        }
        this.price = accommodation.getPrice();
        this.confirmationType = accommodation.getConfirmationType();
        this.image = accommodation.getImage();
        this.approved =accommodation.getApproved();
    }

    public Accommodation(Accommodation accommodation) {
        this.accommodationID = accommodation.getAccommodationID();
        this.name = accommodation.getName();
        this.description = accommodation.getDescription();
        this.location = accommodation.getLocation();
        this.amenities = accommodation.getAmenities();
        this.capacity = new Capacity(accommodation.getCapacity());
        this.accommodationType = accommodation.getAccommodationType();
        this.owner = accommodation.getOwner();
        this.availabilityPeriods = new HashSet<AvailabilityPeriod>();
        for (AvailabilityPeriod availabilityPeriod : accommodation.getAvailabilityPeriods()) {
            this.availabilityPeriods.add(new AvailabilityPeriod(availabilityPeriod));
        }
        this.price = accommodation.getPrice();
        this.confirmationType = accommodation.getConfirmationType();
        this.image = accommodation.getImage();
        this.approved = accommodation.getApproved();
    }

    private Long accommodationID;

    private String name;

    private String description;

    private Location location;

    private Set<Amenity> amenities = new HashSet<>();

    private Capacity capacity;

    private Set<AccommodationType> accommodationType;

    private User owner;

    private Set<AvailabilityPeriod> availabilityPeriods = new HashSet<>();

    private double price;

    private boolean approved;

    private String confirmationType;

    private Bitmap image;

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
    public Boolean getApproved(){
        return this.approved;
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

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
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

    public Set<AvailabilityPeriod> getAvailabilityPeriods() {
        return availabilityPeriods;
    }

    public void setAvailabilityPeriods(HashSet<AvailabilityPeriod> availabilityPeriods) {
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

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
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
                '}';
    }

    public void copyValues(Accommodation accommodation) {
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
    }


    public Long checkAvailability(LocalDate starDate, LocalDate endDate) {
        Long price = 0L;
        for (LocalDate date = starDate; date.isBefore(endDate); date = date.plusDays(1)) {
            boolean found = false;
            for (AvailabilityPeriod availabilityPeriod : this.availabilityPeriods) {
                if (availabilityPeriod.getDate().equals(date)) {
                    found = true;
                    price += availabilityPeriod.getPrice();
                    break;
                }
            }
            if (!found) {
                return null;
            }
        }
        return price;
    }

    // New method to check if the accommodation matches search criteria
    public boolean matchesSearchCriteria(String searchLocation, Long persons, String startDate, String endDate, Long minPrice, Long maxPrice, List<String> amenities, List<String> accommodationTypes, DateTimeFormatter formatter) {
        return matchesLocation(searchLocation) &&
                matchesCapacity(persons) &&
                matchesAvailability(startDate, endDate, minPrice, maxPrice, formatter) &&
                matchesAmenities(amenities) &&
                matchesAccommodationType(accommodationTypes);
    }

    // New method to check if the location matches the search location
    private boolean matchesLocation(String searchLocation) {
        String locationString = location.getAddress() + location.getCountry();
        System.out.println(locationString.toLowerCase().contains(searchLocation.toLowerCase()));
        return locationString.toLowerCase().contains(searchLocation.toLowerCase());
    }

    // New method to check if the capacity matches the persons
    private boolean matchesCapacity(Long persons) {
        System.out.println(capacity.getMaxGuests() >= persons && capacity.getMinGuests() <= persons);
        return capacity.getMaxGuests() >= persons && capacity.getMinGuests() <= persons;
    }

    // New method to check if the availability matches the date range and price range
    private boolean matchesAvailability(String startDate, String endDate, Long minPrice, Long maxPrice, DateTimeFormatter formatter) {
        Long fullPrice = checkAvailability(LocalDate.parse(startDate, formatter), LocalDate.parse(endDate, formatter));
        System.out.println(fullPrice != null && fullPrice >= minPrice && fullPrice <= maxPrice);
        return fullPrice != null && fullPrice >= minPrice && fullPrice <= maxPrice;
    }

    // New method to check if all amenities are present
    private boolean matchesAmenities(List<String> amenities) {
        for (String amenity : amenities) {
            if (!this.amenities.contains(Amenity.valueOf(amenity))) {
                System.out.println(false);
                return false;
            }
        }
        System.out.println(true);
        return true;
    }

    // New method to check if the accommodation type matches
    private boolean matchesAccommodationType(List<String> accommodationTypes) {
        for (String accommodationType : accommodationTypes) {
            if (!this.accommodationType.equals(AccommodationType.valueOf(accommodationType))) {
                System.out.println(false);
                return false;
            }
        }
        System.out.println(true);
        return true;
    }
}
