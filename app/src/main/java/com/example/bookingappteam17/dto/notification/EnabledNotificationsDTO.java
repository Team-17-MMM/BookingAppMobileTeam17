package com.example.bookingappteam17.dto.notification;

import java.util.Set;

public class EnabledNotificationsDTO {
    private Long id;
    private boolean createReservationRequest;
    private boolean cancelReservation;
    private boolean rateUser;
    private boolean rateAccommodation;
    private boolean reservationRequestRespond;
    private Long userId;

    private Set<Long> favoriteAccommodations;

    public EnabledNotificationsDTO(Long id, Set<Long> favoriteAccommodations, boolean createReservationRequest, boolean cancelReservation, boolean rateUser, boolean rateAccommodation, boolean reservationRequestRespond, Long userId) {
        this.id = id;
        this.createReservationRequest = createReservationRequest;
        this.cancelReservation = cancelReservation;
        this.rateUser = rateUser;
        this.rateAccommodation = rateAccommodation;
        this.reservationRequestRespond = reservationRequestRespond;
        this.userId = userId;
        this.favoriteAccommodations = favoriteAccommodations;
    }

    public EnabledNotificationsDTO(){}


    public Set<Long> getFavoriteAccommodations() {
        return favoriteAccommodations;
    }

    public void setFavoriteAccommodations(Set<Long> favoriteAccommodations) {
        this.favoriteAccommodations = favoriteAccommodations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isCreateReservationRequest() {
        return createReservationRequest;
    }

    public void setCreateReservationRequest(boolean createReservationRequest) {
        this.createReservationRequest = createReservationRequest;
    }

    public boolean isCancelReservation() {
        return cancelReservation;
    }

    public void setCancelReservation(boolean cancelReservation) {
        this.cancelReservation = cancelReservation;
    }

    public boolean isRateUser() {
        return rateUser;
    }

    public void setRateUser(boolean rateUser) {
        this.rateUser = rateUser;
    }

    public boolean isRateAccommodation() {
        return rateAccommodation;
    }

    public void setRateAccommodation(boolean rateAccommodation) {
        this.rateAccommodation = rateAccommodation;
    }

    public boolean isReservationRequestRespond() {
        return reservationRequestRespond;
    }

    public void setReservationRequestRespond(boolean reservationRequestRespond) {
        this.reservationRequestRespond = reservationRequestRespond;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
