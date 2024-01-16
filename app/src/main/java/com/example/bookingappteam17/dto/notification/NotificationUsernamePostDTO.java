package com.example.bookingappteam17.dto.notification;


import com.example.bookingappteam17.enums.notification.NotificationType;

public class NotificationUsernamePostDTO {
    private String username;
    private NotificationType notificationType;
    private boolean shown;

    public NotificationUsernamePostDTO() {
    }

    public NotificationUsernamePostDTO(String username, NotificationType notificationType, boolean shown) {
        this.username = username;
        this.notificationType = notificationType;
        this.shown = shown;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

}
