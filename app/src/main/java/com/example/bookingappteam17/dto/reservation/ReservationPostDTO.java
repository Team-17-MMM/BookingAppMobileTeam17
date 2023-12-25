package com.example.bookingappteam17.dto.reservation;

import com.example.bookingappteam17.enums.ReservationStatus;
import com.example.bookingappteam17.model.Reservation;

import java.time.LocalDate;

public class ReservationPostDTO {

    private Long userID;
    private Long accommodationID;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReservationStatus status;
    private int price;


    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long guestID) {
        this.userID = guestID;
    }

    public Long getAccommodationID() {
        return accommodationID;
    }

    public void setAccommodationID(Long accommodationID) {
        this.accommodationID = accommodationID;
    }

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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ReservationPostDTO(Long userID, Long accommodationID, LocalDate startDate, LocalDate endDate, ReservationStatus status, int price) {
        this.userID = userID;
        this.accommodationID = accommodationID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
    }

    public ReservationPostDTO(Reservation reservation){
        this.userID = reservation.getUserID();
        this.accommodationID = reservation.getAccommodationID();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.status = reservation.getStatus();
        this.price = reservation.getPrice();
    }
}
