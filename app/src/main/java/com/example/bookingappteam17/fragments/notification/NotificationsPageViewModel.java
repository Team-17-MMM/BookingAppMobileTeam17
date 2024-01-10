package com.example.bookingappteam17.fragments.notification;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;
import com.example.bookingappteam17.dto.notification.NotificationDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsPageViewModel extends ViewModel {
    private MutableLiveData<List<NotificationDTO>> notificationsLiveData = new MutableLiveData<>();

    public LiveData<List<NotificationDTO>> getNotificationsLiveData() {
        return notificationsLiveData;
    }

    public void loadNotifications(String username, Long id) {
        Call<HashSet<NotificationDTO>> call = ClientUtils.accommodationService.getUserNotificationsEnabled(id);
        call.enqueue(new Callback<HashSet<NotificationDTO>>() {
            @Override
            public void onResponse(Call<HashSet<NotificationDTO>> call, Response<HashSet<NotificationDTO>> response) {
                if (response.isSuccessful()) {
                    HashSet<NotificationDTO> notifications = response.body();
                    List<NotificationDTO> accommodations = convertToDTOList(notifications);
                    notificationsLiveData.setValue(accommodations);
                }
            }

            @Override
            public void onFailure(Call<HashSet<NotificationDTO>> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private List<NotificationDTO> convertToDTOList(HashSet<NotificationDTO> notificationsDto) {
        List<NotificationDTO> notifications = new ArrayList<>();
        for (NotificationDTO accommodationCardRDTO : notificationsDto) {
            if(!accommodationCardRDTO.isShown()){
                notifications.add(accommodationCardRDTO);
            }
        }
        return notifications;
    }
}
