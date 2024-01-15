package com.example.bookingappteam17.fragments.review;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.review.AccommodationReviewDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveReviewPageViewModel extends ViewModel {
    private MutableLiveData<List<AccommodationReviewDTO>> reviewsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<AccommodationReviewDTO>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public void loadReviews() {
        Call<HashSet<AccommodationReviewDTO>> call = ClientUtils.reviewService.getReviews();
        call.enqueue(new Callback<HashSet<AccommodationReviewDTO>>() {
            @Override
            public void onResponse(Call<HashSet<AccommodationReviewDTO>> call, Response<HashSet<AccommodationReviewDTO>> response) {
                if (response.isSuccessful()) {
                    HashSet<AccommodationReviewDTO> accommodationReviewDTOS = response.body();
                    accommodationReviewDTOS.removeIf(AccommodationReviewDTO::isApproved);
                    List<AccommodationReviewDTO> reviews = convertToDTOList(accommodationReviewDTOS);
                    reviewsLiveData.setValue(reviews);
                }
            }

            @Override
            public void onFailure(Call<HashSet<AccommodationReviewDTO>> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private List<AccommodationReviewDTO> convertToDTOList(HashSet<AccommodationReviewDTO> AccommodationReviewDTOS) {
        List<AccommodationReviewDTO> reviews = new ArrayList<>();
        reviews.addAll(AccommodationReviewDTOS);
        return reviews;
    }
}

