package com.example.bookingappteam17.dto.accommodation;

import java.time.LocalDate;

public class AvailabilityPeriodRangeDTO {

    private LocalDate startDate;

    private LocalDate endDate;
    private Long price;

    public AvailabilityPeriodRangeDTO(LocalDate startDate, LocalDate endDate, Long price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }

    public AvailabilityPeriodRangeDTO(){}

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
