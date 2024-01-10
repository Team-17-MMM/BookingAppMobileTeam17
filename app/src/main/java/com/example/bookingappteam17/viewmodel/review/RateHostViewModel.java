package com.example.bookingappteam17.viewmodel.review;

import android.util.Log;
import android.widget.RatingBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityRateHostBinding;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.dto.user.UserInfoDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateHostViewModel extends ViewModel {
    private MutableLiveData<List<HostReviewDTO>> hostReviewsLiveData = new MutableLiveData<>();

    public LiveData<List<HostReviewDTO>> getHostReviewsLiveData() {
        return hostReviewsLiveData;
    }

    public void loadHostReviews(String hostUsername, RatingBar binding) {
        Call<HashSet<HostReviewDTO>> call = ClientUtils.reviewService.getHostReviews(hostUsername);
        call.enqueue(new Callback<HashSet<HostReviewDTO>>() {
            @Override
            public void onResponse(Call<HashSet<HostReviewDTO>> call, Response<HashSet<HostReviewDTO>> response) {
                if (response.isSuccessful()) {
                    HashSet<HostReviewDTO> hostReviews = response.body();
                    binding.setRating(calculateRating(hostReviews));
                    hostReviewsLiveData.setValue(new ArrayList<>(Objects.requireNonNull(hostReviews)));
                }
            }

            @Override
            public void onFailure(Call<HashSet<HostReviewDTO>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void submitHostReview(HostReviewDTO review) {
        Call<Void> call = ClientUtils.reviewService.submitHostReview(review);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    public void loadUserInfo(String username, ActivityRateHostBinding binding) {
        Call<UserInfoDTO> call = ClientUtils.userService.getUserInfo(username);
        call.enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (response.isSuccessful()) {
                    UserInfoDTO userInfoDTO = response.body();
                    Log.d("TAG", "onResponse: " + userInfoDTO.getUsername());
                    binding.textHostName.setText(userInfoDTO.getName());
                    binding.textHostSurname.setText(userInfoDTO.getLastname());
                    binding.textHostUsername.setText(userInfoDTO.getUsername());
                }

            }

            @Override
            public void onFailure(Call<UserInfoDTO> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }


    private float calculateRating(HashSet<HostReviewDTO> hostReviews) {
        float rating = 0;
        for (HostReviewDTO review : hostReviews) {
            rating += review.getRating();
        }
        return rating / hostReviews.size();
    }
}
