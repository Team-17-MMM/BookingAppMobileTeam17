package com.example.bookingappteam17.model;

public class Capacity {
    private int minOccupancy;
    private int maxOccupancy;

    public Capacity(int minOccupancy, int maxOccupancy) {
        this.minOccupancy = minOccupancy;
        this.maxOccupancy = maxOccupancy;
    }

    public Capacity(){}

    public int getMinOccupancy() {
        return minOccupancy;
    }

    public void setMinOccupancy(int minOccupancy) {
        this.minOccupancy = minOccupancy;
    }

    public int getMaxOccupancy() {
        return maxOccupancy;
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy = maxOccupancy;
    }
}
