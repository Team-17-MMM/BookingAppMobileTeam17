package com.example.bookingappteam17.model.accommodation;


import com.example.bookingappteam17.dto.accommodation.CapacityDTO;

public class Capacity {

    public Capacity() {}

    public Capacity(int minGuests, int maxGuests) {
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
    }

    public Capacity(CapacityDTO capacity) {
        this.minGuests = capacity.getMinGuests();
        this.maxGuests = capacity.getMaxGuests();
    }

    public Capacity(Capacity capacity) {
        this.minGuests = capacity.getMinGuests();
        this.maxGuests = capacity.getMaxGuests();
    }
    private int minGuests;
    private int maxGuests;


    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    @Override
    public String toString() {
        return "Capacity{" +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                '}';
    }
}
