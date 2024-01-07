package com.example.bookingappteam17.fragments.reservation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;
import com.example.bookingappteam17.dto.reservation.ReservationInfoDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationViewModel extends ViewModel {

    private MutableLiveData<List<ReservationInfoDTO>> reservationsLiveData = new MutableLiveData<>();

    public LiveData<List<ReservationInfoDTO>> getReservationsLiveData() {
        return reservationsLiveData;
    }

    public void loadReservations(Long id, String role) {
        if (role.equals("HOST")) {

            Call<HashSet<ReservationInfoDTO>> call = ClientUtils.reservationService.getHostReservations(id);
            call.enqueue(new Callback<HashSet<ReservationInfoDTO>>() {
                @Override
                public void onResponse(Call<HashSet<ReservationInfoDTO>> call, Response<HashSet<ReservationInfoDTO>> response) {
                    if (response.isSuccessful()) {
                        HashSet<ReservationInfoDTO> reservationInfoDTOS = response.body();
                        reservationsLiveData.setValue(new ArrayList<>(reservationInfoDTOS));
                    }
                }

                @Override
                public void onFailure(Call<HashSet<ReservationInfoDTO>> call, Throwable t) {
                    // Handle failure
                    Log.e("ERROR", t.getMessage());
                }
            });
        } else {
            Call<HashSet<ReservationInfoDTO>> call = ClientUtils.reservationService.getUserInfoReservations(id);
            call.enqueue(new Callback<HashSet<ReservationInfoDTO>>() {
                @Override
                public void onResponse(Call<HashSet<ReservationInfoDTO>> call, Response<HashSet<ReservationInfoDTO>> response) {
                    if (response.isSuccessful()) {
                        HashSet<ReservationInfoDTO> reservationInfoDTOS = response.body();
                        reservationsLiveData.setValue(new ArrayList<>(reservationInfoDTOS));
                    }
                }

                @Override
                public void onFailure(Call<HashSet<ReservationInfoDTO>> call, Throwable t) {
                    // Handle failure
                    Log.e("ERROR", t.getMessage());
                }
            });
        }

    }

    public void updateReservationList(List<ReservationInfoDTO> reservations){
        reservationsLiveData.setValue(reservations);
    }

}
