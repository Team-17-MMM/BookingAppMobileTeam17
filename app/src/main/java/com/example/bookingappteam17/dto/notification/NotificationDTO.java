package com.example.bookingappteam17.dto.notification;

import com.example.bookingappteam17.enums.notification.NotificationType;

public class NotificationDTO {

    private Long id;
    private Long userID;
    private NotificationType notificationType;
    private boolean shown;

    public NotificationDTO() {
    }


    public NotificationDTO(Long id, Long userID, NotificationType notificationType, boolean shown) {
        this.id = id;
        this.userID = userID;
        this.notificationType = notificationType;
        this.shown = shown;
    }

    public NotificationDTO(Long userID, NotificationType notificationType, boolean shown) {
        this.userID = userID;
        this.notificationType = notificationType;
        this.shown = shown;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public boolean isShown() {
        return shown;
    }

    public void setShown(boolean shown) {
        this.shown = shown;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id=" + id +
                ", userID=" + userID +
                ", notificationType=" + notificationType +
                ", shown=" + shown +
                '}';
    }

}
