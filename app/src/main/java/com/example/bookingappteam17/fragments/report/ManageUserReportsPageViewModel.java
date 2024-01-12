package com.example.bookingappteam17.fragments.report;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.user.UserReportDTO;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageUserReportsPageViewModel extends ViewModel {
    private MutableLiveData<List<UserReportDTO>> reviewsLiveData = new MutableLiveData<>();
    public MutableLiveData<List<UserReportDTO>> getReviewsLiveData() {
        return reviewsLiveData;
    }

    public void loadReviews() {
        Call<HashSet<UserReportDTO>> call = ClientUtils.reviewService.getReportedUsers();
        call.enqueue(new Callback<HashSet<UserReportDTO>>() {
            @Override
            public void onResponse(Call<HashSet<UserReportDTO>> call, Response<HashSet<UserReportDTO>> response) {
                if (response.isSuccessful()) {
                    HashSet<UserReportDTO> reportedReviewCardDTOS = response.body();
                    List<UserReportDTO> reviews = convertToDTOList(reportedReviewCardDTOS);
                    reviewsLiveData.setValue(reviews);
                }
            }

            @Override
            public void onFailure(Call<HashSet<UserReportDTO>> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private List<UserReportDTO> convertToDTOList(HashSet<UserReportDTO> reportedReviewCardDTOS) {
        List<UserReportDTO> reviews = new ArrayList<>();
        for (UserReportDTO reportedReviewCardDTO : reportedReviewCardDTOS) {
            reviews.add(reportedReviewCardDTO);
        }

        return reviews;
    }

}
