package com.example.bookingappteam17.dto.reservation;

import com.example.bookingappteam17.enums.reservation.ReservationStatus;

import java.time.LocalDate;

public class ReservationInfoDTO {
    private Long reservationID;
    private String userFirstName;
    private String userLastName;
    private String accommodationName;
    private String startDate;
    private String endDate;
    private ReservationStatus status;
    private int price;

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
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


    public ReservationInfoDTO(Long reservationID, String userFirstName, String userLastName, String accommodationName, String startDate, String endDate, ReservationStatus status, int price) {
        this.reservationID = reservationID;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.accommodationName = accommodationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
    }
}
