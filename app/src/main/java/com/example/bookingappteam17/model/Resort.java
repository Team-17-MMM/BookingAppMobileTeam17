package com.example.bookingappteam17.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.bookingappteam17.enums.Amenities;
import com.example.bookingappteam17.enums.ResortType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Resort implements Parcelable {
    private Long id;
    private String name;
    private String description;
    private String city;
    private int image;
    private int price;
    private List<List<LocalDate>> reservedDates;
    private ResortType type;
    private List<Amenities> amenities;
    private Capacity capacity;
    private List<Review> reviews;
    public Resort(Long id, String name, String description, String city, int image, int price, List<List<LocalDate>> reservedDates, ResortType type, List<Amenities> amenities, Capacity capacity, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.image = image;
        this.price = price;
        this.reservedDates = reservedDates;
        this.type = type;
        this.amenities = amenities;
        this.capacity = capacity;
        this.reviews = reviews;
        //dodaj capacity neki i uradi filtriranje po tome, treba da se urade i recenzije neke ili ocene da metnem na resort, lista ocena npr
        //napravi komentar klasu isto, da ima id resorta
    }

    public Resort(Long id, String name, String description, String city, int image, int price, Capacity capacity, List<Amenities> amenitites, ResortType type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.image = image;
        this.price = price;
        this.capacity = capacity;
        this.amenities = amenitites;
        this.type = type;
        this.reservedDates = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public Resort(){
        reservedDates = new ArrayList<>();
        amenities = new ArrayList<>();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public int getPrice(){return price;}
    public void setPrice(int price){this.price=price;}

    public List<List<LocalDate>> getReservedDates() {
        return reservedDates;
    }

    public void setReservedDates(List<List<LocalDate>> reservedDates) {
        this.reservedDates = reservedDates;
    }

    public ResortType getType() {
        return type;
    }

    public void setType(ResortType type) {
        this.type = type;
    }

    public List<Amenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenities> amenities) {
        this.amenities = amenities;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Resort{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", city='" + city + '\'' +
                ", image=" + image +
                ", price=" + price +
                ", reservedDates=" + reservedDates +
                '}';
    }

    protected Resort(Parcel in){
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        city = in.readString();
        image = in.readInt();
        price = in.readInt();

        // Reading reservedDates
        int numPeriods = in.readInt();
        reservedDates = new ArrayList<>();
        for (int i = 0; i < numPeriods; i++) {
            int numDates = in.readInt();
            List<LocalDate> period = new ArrayList<>();
            for (int j = 0; j < numDates; j++) {
                period.add((LocalDate) in.readSerializable());
            }
            reservedDates.add(period);
        }
        type = ResortType.valueOf(in.readString());
        amenities = new ArrayList<>();
        int numAmenities = in.readInt();
        for (int i = 0; i < numAmenities; i++) {
            amenities.add(Amenities.valueOf(in.readString()));
        }
        capacity = in.readParcelable(Capacity.class.getClassLoader());
        reviews = in.createTypedArrayList(Review.CREATOR);
    }
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(city);
        dest.writeInt(image);
        dest.writeInt(price);

        dest.writeInt(reservedDates.size()); // Write the size of the outer list
        for (List<LocalDate> period : reservedDates) {
            dest.writeInt(period.size()); // Write the size of each inner list
            for (LocalDate date : period) {
                dest.writeSerializable(date); // Write each date as serializable
            }
        }
        dest.writeString(type.name()); // Assuming ResortType is an enum
        dest.writeList(amenities);
        dest.writeParcelable(capacity, flags);
        dest.writeTypedList(reviews);
    }
    public static final Creator<Resort> CREATOR = new Creator<Resort>() {
        @Override
        public Resort createFromParcel(Parcel in) {
            return new Resort(in);
        }

        @Override
        public Resort[] newArray(int size) {
            return new Resort[size];
        }
    };
}
