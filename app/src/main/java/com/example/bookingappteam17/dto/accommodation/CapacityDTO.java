package com.example.bookingappteam17.dto.accommodation;


import com.example.bookingappteam17.model.Capacity;

public class CapacityDTO {
    private int minGuests;
    private int maxGuests;

    public CapacityDTO() {}

    public CapacityDTO(int minGuests, int maxGuests) {
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
    }

    public CapacityDTO(Capacity capacity) {
        this.minGuests = capacity.getMinGuests();
        this.maxGuests = capacity.getMaxGuests();
    }

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
        return "CapacityDTO{" +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                '}';
    }
}
