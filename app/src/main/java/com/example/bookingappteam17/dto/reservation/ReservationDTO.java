package com.example.bookingappteam17.dto.reservation;

import com.example.bookingappteam17.enums.reservation.ReservationStatus;

public class ReservationDTO {

    private Long reservationID;
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

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

    public ReservationDTO(Long userID, Long accommodationID, String startDate, String endDate, ReservationStatus status, int price) {
        this.userID = userID;
        this.accommodationID = accommodationID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
    }

    public ReservationDTO(Long reservationID,Long userID, Long accommodationID, String startDate, String endDate, ReservationStatus status, int price) {
        this.reservationID = reservationID;
        this.userID = userID;
        this.accommodationID = accommodationID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
    }

}
