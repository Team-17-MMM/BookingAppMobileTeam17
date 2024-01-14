package com.example.bookingappteam17.viewmodel.review;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityRateAccommodationBinding;
import com.example.bookingappteam17.databinding.ActivityRateHostBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.review.AccommodationReviewDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateAccommodationViewModel extends ViewModel {
    private MutableLiveData<List<AccommodationReviewDTO>> accommodationReviewsLiveData = new MutableLiveData<>();

    public LiveData<List<AccommodationReviewDTO>> getAccommodationReviewsLiveData() {
        return accommodationReviewsLiveData;
    }

    public void loadAccommodationReviews(Long accommodationID, ActivityRateAccommodationBinding binding) {
        Call<HashSet<AccommodationReviewDTO>> call = ClientUtils.reviewService.getAccommodationReviews(accommodationID);
        call.enqueue(new Callback<HashSet<AccommodationReviewDTO>>() {
            @Override
            public void onResponse(Call<HashSet<AccommodationReviewDTO>> call, Response<HashSet<AccommodationReviewDTO>> response) {
                if (response.isSuccessful()) {
                    HashSet<AccommodationReviewDTO> accommodationReviews = response.body();
                    accommodationReviews.removeIf(accommodationReviewDTO -> !accommodationReviewDTO.isApproved());
                    float rating = calculateRating(accommodationReviews);
                    if(Float.isNaN(rating)){
                        rating = 0;
                    }
                    binding.ratingBarAccommodation.setRating(rating);
                    String ratingText = "(" + rating + "/5)";
                    binding.ratingBarAccommodationText.setText(ratingText);

                    accommodationReviewsLiveData.setValue(new ArrayList<>(Objects.requireNonNull(accommodationReviews)));
                }
            }

            @Override
            public void onFailure(Call<HashSet<AccommodationReviewDTO>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void submitAccommodationReview(AccommodationReviewDTO review, Context context) {
        Call<AccommodationReviewDTO> call = ClientUtils.reviewService.submitAccommodationReview(review);
        call.enqueue(new Callback<AccommodationReviewDTO>() {
            @Override
            public void onResponse(Call<AccommodationReviewDTO> call, Response<AccommodationReviewDTO> response) {
                if (response.isSuccessful()) {
                    AccommodationReviewDTO accommodationReviewDTO = response.body();
                    Toast.makeText(context, "Sent review. Waiting for approval", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AccommodationReviewDTO> call, Throwable t) {
                Toast.makeText(context, "Review failed. Try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void loadAccommodationInfo(Long id, ActivityRateAccommodationBinding binding) {
        Call<AccommodationDTO> call = ClientUtils.accommodationService.getAccommodation(id);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    AccommodationDTO AccommodationDTO = response.body();
                    binding.textAccommodationName.setText(AccommodationDTO.getName());
                }

            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void setCommentVisibility(Long accommodationID, Long userID, ActivityRateAccommodationBinding binding) {
        Call<HashSet<AccommodationDTO>> call = ClientUtils.reservationService.getAccommodationsInfo(userID);
        call.enqueue(new Callback<HashSet<AccommodationDTO>>() {
            @Override
            public void onResponse(Call<HashSet<AccommodationDTO>> call, Response<HashSet<AccommodationDTO>> response) {
                if (response.isSuccessful()) {
                    for(AccommodationDTO accommodationDTO: response.body()){
                        if(accommodationDTO.getAccommodationID().equals(accommodationID)){
                            binding.commentSection.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    binding.commentSection.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<HashSet<AccommodationDTO>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                // Handle the failure case if needed
            }
        });
    }


    private float calculateRating(HashSet<AccommodationReviewDTO> accommodationReviews) {
        float rating = 0;
        for (AccommodationReviewDTO review : accommodationReviews) {
            rating += review.getGrade();
        }
        return rating / accommodationReviews.size();
    }
}

