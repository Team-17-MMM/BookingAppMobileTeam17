package com.example.bookingappteam17.enums.notification;

public enum NotificationType {

    CREATE_RESERVATION_REQUEST("Good news! You have received a new reservation request."),
    CANCEL_RESERVATION("Your reservation has been canceled."),
    RATE_USER("You have received a new review from a guest."),
    RATE_ACCOMMODATION("A guest has reviewed your accommodation."),
    RESERVATION_REQUEST_RESPOND("Good news! The host has responded to your reservation request.");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
