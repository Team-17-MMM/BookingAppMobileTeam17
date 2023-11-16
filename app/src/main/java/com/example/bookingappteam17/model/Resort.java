package com.example.bookingappteam17.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Resort implements Parcelable {
    private Long id;
    private String name;
    private String description;
    private String city;
    private int image;
    private int price;

    public Resort(Long id, String name, String description, String city, int image, int price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.city = city;
        this.image = image;
        this.price = price;
    }

    public Resort(){}

    protected Resort(Parcel in){
        id = in.readLong();
        name = in.readString();
        description = in.readString();
        city = in.readString();
        image = in.readInt();
        price = in.readInt();
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
    @Override
    public int describeContents() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Resort{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(city);
        dest.writeString(description);
        dest.writeInt(image);
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
