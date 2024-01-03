package com.example.bookingappteam17.dto.accommodation;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.enums.accommodation.AccommodationType;
import com.example.bookingappteam17.enums.accommodation.Amenity;
import com.example.bookingappteam17.model.accommodation.Accommodation;
import com.example.bookingappteam17.model.accommodation.AvailabilityPeriod;
import com.example.bookingappteam17.model.accommodation.Location;
import com.example.bookingappteam17.model.user.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AccommodationDTO implements Serializable, Parcelable {

    private Long accommodationID;
    private String name;
    private String description;
    private Location location;
    private Set<Amenity> amenities = new HashSet<>();
    private CapacityDTO capacity;
    private Set<AccommodationType> accommodationType = new HashSet<>();
    private User owner;
    private Set<AvailabilityPeriodDTO> availabilityPeriods = new HashSet<>();
    private double price;
    private String confirmationType;
    private String image;
    private Boolean approved;
    private Long updateAccommodationID;
    private Boolean guestPriced;
    private Long cancelingDays;

    protected AccommodationDTO(Parcel in) {
        if (in.readByte() == 0) {
            accommodationID = null;
        } else {
            accommodationID = in.readLong();
        }
        name = in.readString();
        description = in.readString();
        owner = in.readParcelable(User.class.getClassLoader());
        price = in.readDouble();
        confirmationType = in.readString();
        image = in.readParcelable(String.class.getClassLoader());
        byte tmpApproved = in.readByte();
        approved = tmpApproved == 0 ? null : tmpApproved == 1;
        updateAccommodationID = in.readLong();
    }

    public static final Creator<AccommodationDTO> CREATOR = new Creator<AccommodationDTO>() {
        @Override
        public AccommodationDTO createFromParcel(Parcel in) {
            return new AccommodationDTO(in);
        }

        @Override
        public AccommodationDTO[] newArray(int size) {
            return new AccommodationDTO[size];
        }
    };

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

    public Long getUpdateAccommodationID() {
        return updateAccommodationID;
    }

    public void setUpdateAccommodationID(Long updateAccommodationID) {
        this.updateAccommodationID = updateAccommodationID;
    }

    public Boolean getGuestPriced() {
        return guestPriced;
    }

    public void setGuestPriced(Boolean guestPriced) {
        this.guestPriced = guestPriced;
    }

    public Long getCancelingDays() {
        return cancelingDays;
    }

    public void setCancelingDays(Long cancelingDays) {
        this.cancelingDays = cancelingDays;
    }

    public AccommodationDTO() {}

    public AccommodationDTO(
            Long accommodationID,
            String image,
            Boolean approved,
            String name,
            String description,
            Location location,
            Set<Amenity> amenities,
            CapacityDTO capacity,
            Set<AccommodationType> accommodationType,
            User owner,
            Set<AvailabilityPeriodDTO> availabilityPeriods,
            double price,
            String confirmationType,
            Long updateAccommodationID,
            Boolean guestPriced,
            Long cancelingDays) {

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
        this.image = null;
        this.approved = approved;
        this.updateAccommodationID = updateAccommodationID;
        this.guestPriced = guestPriced;
        this.cancelingDays = cancelingDays;
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
        this.image = null;
        this.approved = accommodation.getApproved();
        this.updateAccommodationID = accommodation.getUpdateAccommodationID();
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
        this.image = null;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (accommodationID == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(accommodationID);
        }
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeParcelable(owner, i);
        parcel.writeDouble(price);
        parcel.writeString(confirmationType);
        parcel.writeString(image);
        parcel.writeByte((byte) (approved == null ? 0 : approved ? 1 : 2));
    }
}
