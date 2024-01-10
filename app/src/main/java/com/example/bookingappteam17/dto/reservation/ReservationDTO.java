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
    private int occupancy;

    public Long getReservationID() {
        return reservationID;
    }

    public void setReservationID(Long reservationID) {
        this.reservationID = reservationID;
    }

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

    public int getOccupancy() {
        return occupancy;
    }

    public void setOccupancy(int occupancy) {
        this.occupancy = occupancy;
    }

    public ReservationDTO(Long userID, Long accommodationID, String startDate, String endDate, ReservationStatus status, int price, int occupancy) {
        this.userID = userID;
        this.accommodationID = accommodationID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.price = price;
        this.occupancy = occupancy;
    }
    

    public void copyValues(ReservationDTO reservation){
        this.setUserID(reservation.getUserID());
        this.setAccommodationID(reservation.getAccommodationID());
        this.setStartDate(reservation.getStartDate());
        this.setEndDate(reservation.getEndDate());
        this.setStatus(reservation.getStatus());
        this.setPrice(reservation.getPrice());
        this.setOccupancy(reservation.getOccupancy());
    }

}
