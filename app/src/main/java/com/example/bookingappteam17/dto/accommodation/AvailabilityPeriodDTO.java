package com.example.bookingappteam17.dto.accommodation;


import com.example.bookingappteam17.model.AvailabilityPeriod;

import java.time.LocalDate;

public class AvailabilityPeriodDTO {
    private LocalDate date;
    private Long price;

    public AvailabilityPeriodDTO() {}

    public AvailabilityPeriodDTO(LocalDate date, Long price) {
        this.date = date;
        this.price = price;
    }

    public AvailabilityPeriodDTO(AvailabilityPeriod availabilityPeriod) {
        this.date = availabilityPeriod.getDate();
        this.price = availabilityPeriod.getPrice();
    }

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
        return "AvailabilityPeriodDTO{" +
                ", startDate=" + date +
                ", endDate=" + price +
                '}';
    }
}
