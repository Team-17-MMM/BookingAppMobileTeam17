package com.example.bookingappteam17.services.notification;

import com.example.bookingappteam17.dto.notification.NotificationDTO;
import com.example.bookingappteam17.dto.notification.NotificationUsernamePostDTO;
import com.example.bookingappteam17.dto.reservation.ReservationInfoDTO;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface INotificationService {
    @POST("notification/usernamePost")
    Call<NotificationDTO> createNotificationsByUsername(@Body NotificationUsernamePostDTO notificationDTO);


    @POST("notification")
    Call<NotificationDTO> createNotification(@Body NotificationDTO notificationDTO);

}
