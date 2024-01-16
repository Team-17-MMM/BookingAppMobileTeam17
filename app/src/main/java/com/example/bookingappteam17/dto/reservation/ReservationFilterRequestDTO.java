package com.example.bookingappteam17.dto.reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationFilterRequestDTO {
    List<ReservationInfoDTO> reservations = new ArrayList<>();
    Long userID;
    String startDate;
    String endDate;
    String accommodationName;
    String[] statuses;

    public List<ReservationInfoDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationInfoDTO> reservations) {
        this.reservations = reservations;
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public String[] getStatuses() {
        return statuses;
    }

    public void setStatus(String[] statuses) {
        this.statuses = statuses;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public ReservationFilterRequestDTO(){}

    public ReservationFilterRequestDTO(List<ReservationInfoDTO> reservations, Long userID, String startDate, String endDate, String accommodationName, String[] status) {
        this.reservations = reservations;
        this.userID = userID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodationName = accommodationName;
        this.statuses = status;
    }

    public ReservationFilterRequestDTO(Long userID, String startDate, String endDate, String accommodationName, String[] status) {
        this.userID = userID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.accommodationName = accommodationName;
        this.statuses = status;
    }
}
