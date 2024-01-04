package com.example.bookingappteam17.dto.reservation;

import com.example.bookingappteam17.enums.reservation.ReservationStatus;
import com.example.bookingappteam17.model.reservation.Reservation;

import java.time.LocalDate;

public class ReservationPostDTO {

    private Long userID;
    private Long accommodationID;
    private String startDate;
    private String endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public ReservationPostDTO(Long userID, Long accommodationID, String startDate, String endDate, ReservationStatus status, int price) {
        this.userID = userID;
        this.accommodationID = accommodationID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
    }

}
