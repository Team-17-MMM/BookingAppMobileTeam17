package com.example.bookingappteam17.viewmodel.review;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.review.HostReviewDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;

public class RateHostViewModel extends ViewModel {
    private MutableLiveData<List<HostReviewDTO>> hostReviewsLiveData = new MutableLiveData<>();

    public LiveData<List<HostReviewDTO>> getHostReviewsLiveData() {
        return hostReviewsLiveData;
    }

    public void loadHostReviews(String hostUsername) {
        Call<HashSet<HostReviewDTO>> call = ClientUtils.reviewService.getHostReviews(hostUsername);
        call.enqueue(new Callback<HashSet<HostReviewDTO>>() {
            @Override
            public void onResponse(Call<HashSet<HostReviewDTO>> call, retrofit2.Response<HashSet<HostReviewDTO>> response) {
                if (response.isSuccessful()) {
                    HashSet<HostReviewDTO> hostReviews = response.body();
                    hostReviewsLiveData.setValue(new ArrayList<>(Objects.requireNonNull(hostReviews)));
                }
            }

            @Override
            public void onFailure(Call<HashSet<HostReviewDTO>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

}
