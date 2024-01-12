package com.example.bookingappteam17.viewmodel.review;

import android.util.Log;
import android.widget.RatingBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.dto.user.UserInfoDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportUserViewModel extends ViewModel {

    private MutableLiveData<List<UserInfoDTO>> hostReviewsLiveData = new MutableLiveData<>();

    public LiveData<List<UserInfoDTO>> getHostReviewsLiveData() {
        return hostReviewsLiveData;
    }

    public void loadUserForReport(Long id, String role) {
        if(Objects.equals(role, "HOST")){
            Call<HashSet<UserInfoDTO>> call = ClientUtils.reviewService.getReservationsUsers(id);
            call.enqueue(new Callback<HashSet<UserInfoDTO>>() {
                @Override
                public void onResponse(Call<HashSet<UserInfoDTO>> call, Response<HashSet<UserInfoDTO>> response) {
                    if (response.isSuccessful()) {
                        HashSet<UserInfoDTO> hostReviews = response.body();
                        hostReviewsLiveData.setValue(new ArrayList<>(Objects.requireNonNull(hostReviews)));
                    }
                }

                @Override
                public void onFailure(Call<HashSet<UserInfoDTO>> call, Throwable t) {
                    Log.e("ERROR", t.getMessage());
                }
            });
        }
        else if(Objects.equals(role, "GUEST")){
            Call<HashSet<UserInfoDTO>> call = ClientUtils.reviewService.getReservationsHosts(id);
            call.enqueue(new Callback<HashSet<UserInfoDTO>>() {
                @Override
                public void onResponse(Call<HashSet<UserInfoDTO>> call, Response<HashSet<UserInfoDTO>> response) {
                    if (response.isSuccessful()) {
                        HashSet<UserInfoDTO> hostReviews = response.body();
                        hostReviewsLiveData.setValue(new ArrayList<>(Objects.requireNonNull(hostReviews)));
                    }
                }

                @Override
                public void onFailure(Call<HashSet<UserInfoDTO>> call, Throwable t) {
                    Log.e("ERROR", t.getMessage());
                }
            });
        }

    }
}
