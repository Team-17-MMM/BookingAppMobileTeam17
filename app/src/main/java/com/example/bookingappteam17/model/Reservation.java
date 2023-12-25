package com.example.bookingappteam17.model;

import com.example.bookingappteam17.enums.ReservationStatus;

import java.io.Serializable;
import java.time.LocalDate;

public class Reservation implements Serializable {

    private Long reservationID;

    private LocalDate startDate;

    private LocalDate endDate;

    private ReservationStatus status;

    private Long userID;

    private Long accommodationID;

    private int price;

    public Long getReservationID() {
        return reservationID;
    }
    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
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

    public Reservation(Long reservationID, LocalDate startDate, LocalDate endDate, ReservationStatus status, Long userID, Long accommodationID, int price) {
        this.reservationID = reservationID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.userID = userID;
        this.accommodationID = accommodationID;
        this.price = price;
    }
}
