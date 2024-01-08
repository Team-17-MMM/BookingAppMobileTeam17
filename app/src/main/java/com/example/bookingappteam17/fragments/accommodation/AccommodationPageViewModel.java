package com.example.bookingappteam17.fragments.accommodation;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationPageViewModel extends ViewModel {

    private MutableLiveData<List<AccommodationCardDTO>> accommodationsLiveData = new MutableLiveData<>();

    public LiveData<List<AccommodationCardDTO>> getAccommodationsLiveData() {
        return accommodationsLiveData;
    }

    public void loadAccommodations(String username, String role) {
        if (role.equals("HOST")){
            Call<HashSet<AccommodationCardRDTO>> call = ClientUtils.accommodationService.getHostAccommodationsCards(username);
            call.enqueue(new Callback<HashSet<AccommodationCardRDTO>>() {
                @Override
                public void onResponse(Call<HashSet<AccommodationCardRDTO>> call, Response<HashSet<AccommodationCardRDTO>> response) {
                    if (response.isSuccessful()) {
                        HashSet<AccommodationCardRDTO> accommodationCardRDTOS = response.body();
                        List<AccommodationCardDTO> accommodations = convertToDTOList(accommodationCardRDTOS);
                        accommodationsLiveData.setValue(accommodations);
                    }
                }

                @Override
                public void onFailure(Call<HashSet<AccommodationCardRDTO>> call, Throwable t) {
                    // Handle failure
                    Log.e("ERROR", t.getMessage());
                }
            });
        }else{
            Call<HashSet<AccommodationCardRDTO>> call = ClientUtils.accommodationService.getAccommodationsCards();
            call.enqueue(new Callback<HashSet<AccommodationCardRDTO>>() {
                @Override
                public void onResponse(Call<HashSet<AccommodationCardRDTO>> call, Response<HashSet<AccommodationCardRDTO>> response) {
                    if (response.isSuccessful()) {
                        HashSet<AccommodationCardRDTO> accommodationCardRDTOS = response.body();
                        List<AccommodationCardDTO> accommodations = convertToDTOList(accommodationCardRDTOS);
                        accommodationsLiveData.setValue(accommodations);
                    }
                }

                @Override
                public void onFailure(Call<HashSet<AccommodationCardRDTO>> call, Throwable t) {
                    // Handle failure
                    Log.e("ERROR", t.getMessage());
                }
            });
        }

    }

    private List<AccommodationCardDTO> convertToDTOList(HashSet<AccommodationCardRDTO> accommodationCardRDTOS) {
        List<AccommodationCardDTO> accommodations = new ArrayList<>();
        for (AccommodationCardRDTO accommodationCardRDTO : accommodationCardRDTOS) {
            accommodations.add(new AccommodationCardDTO(accommodationCardRDTO));
        }

        return accommodations;
    }
}
