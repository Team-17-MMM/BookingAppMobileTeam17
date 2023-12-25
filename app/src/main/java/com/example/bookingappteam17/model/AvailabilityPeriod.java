package com.example.bookingappteam17.model;

import com.example.bookingappteam17.dto.accommodation.AvailabilityPeriodDTO;

import java.time.LocalDate;

public class AvailabilityPeriod {

    public AvailabilityPeriod() {}

    public AvailabilityPeriod(LocalDate date, Long price) {
        this.date = date;
        this.price = price;
    }

    public AvailabilityPeriod(AvailabilityPeriodDTO availabilityPeriod) {
        this.date = LocalDate.parse(availabilityPeriod.getDate());
        this.price = availabilityPeriod.getPrice();
    }

    public AvailabilityPeriod(AvailabilityPeriod availabilityPeriod) {
        this.date = availabilityPeriod.getDate();
        this.price = availabilityPeriod.getPrice();
    }

    private LocalDate date;
    private Long price;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AvailabilityPeriod{" +
                ", startDate=" + date +
                ", price=" + price +
                '}';
    }
}
