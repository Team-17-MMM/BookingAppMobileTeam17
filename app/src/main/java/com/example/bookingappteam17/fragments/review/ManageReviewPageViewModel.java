package com.example.bookingappteam17.fragments.review;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageReviewPageViewModel extends ViewModel {
    private MutableLiveData<List<ReportedReviewCardDTO>> reviewsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<ReportedReviewCardDTO>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public void loadReviews() {
        Call<HashSet<ReportedReviewCardDTO>> call = ClientUtils.reviewService.getReportedReviews();
        call.enqueue(new Callback<HashSet<ReportedReviewCardDTO>>() {
            @Override
            public void onResponse(Call<HashSet<ReportedReviewCardDTO>> call, Response<HashSet<ReportedReviewCardDTO>> response) {
                if (response.isSuccessful()) {
                    HashSet<ReportedReviewCardDTO> reportedReviewCardDTOS = response.body();
                    List<ReportedReviewCardDTO> reviews = convertToDTOList(reportedReviewCardDTOS);
                    reviewsLiveData.setValue(reviews);
                }
            }

            @Override
            public void onFailure(Call<HashSet<ReportedReviewCardDTO>> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private List<ReportedReviewCardDTO> convertToDTOList(HashSet<ReportedReviewCardDTO> reportedReviewCardDTOS) {
        List<ReportedReviewCardDTO> reviews = new ArrayList<>();
        for (ReportedReviewCardDTO reportedReviewCardDTO : reportedReviewCardDTOS) {
            reviews.add(new ReportedReviewCardDTO(reportedReviewCardDTO));
        }

        return reviews;
    }
}
