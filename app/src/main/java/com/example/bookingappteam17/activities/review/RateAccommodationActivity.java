package com.example.bookingappteam17.activities.review;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.adapters.AccommodationReviewAdapter;
import com.example.bookingappteam17.adapters.HostReviewAdapter;
import com.example.bookingappteam17.databinding.ActivityRateAccommodationBinding;
import com.example.bookingappteam17.databinding.ActivityRateHostBinding;
import com.example.bookingappteam17.dto.review.AccommodationReviewDTO;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.viewmodel.review.RateAccommodationViewModel;
import com.example.bookingappteam17.viewmodel.review.RateHostViewModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RateAccommodationActivity extends AppCompatActivity {
    ActivityRateAccommodationBinding binding;
    private RecyclerView recyclerView;
    private AccommodationReviewAdapter reviewAdapter;
    private RateAccommodationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateAccommodationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.accommodationReviewsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(RateAccommodationViewModel.class);

        Long accommodationID = getIntent().getLongExtra("accommodation_id", 0);
        Long userID = getIntent().getLongExtra("user_id", 0);

        reviewAdapter = new AccommodationReviewAdapter(this, userID, getIntent().getLongExtra("host_id", 0));
        recyclerView.setAdapter(reviewAdapter);

        // Observe changes in hostReviewsLiveData
        viewModel.getAccommodationReviewsLiveData().observe(this, new Observer<List<AccommodationReviewDTO>>() {
            @Override
            public void onChanged(List<AccommodationReviewDTO> accommodationReviewDTOS) {
                // Update the UI with the new list of reviews
                reviewAdapter.setReviews(accommodationReviewDTOS);
            }
        });

        // Load host reviews when the activity is created
        viewModel.loadAccommodationReviews(accommodationID, binding);

        viewModel.loadAccommodationInfo(accommodationID, binding);

        viewModel.setCommentVisibility(accommodationID, userID, binding);

        Button submitReviewButton = binding.btnSubmitComment;
        submitReviewButton.setOnClickListener(v -> {
            if (binding.ratingBar.getRating() == 0 || Objects.requireNonNull(binding.etCommentInput.getText()).toString().equals("")) {
                binding.etCommentInput.setError("Please enter a comment and rating");
                binding.ratingBar.setOutlineSpotShadowColor(getResources().getColor(R.color.red));
            } else {
                AccommodationReviewDTO review = new AccommodationReviewDTO();
                review.setComment(binding.etCommentInput.getText().toString());
                review.setGrade((int) binding.ratingBar.getRating());
                review.setReviewer(userID);
                review.setAccommodationID(accommodationID);
                review.setReviewDate(LocalDate.now().toString());
                review.setApproved(false);
                viewModel.submitAccommodationReview(review,this);

                // clear the comment input field and rating bar and close error messages
                binding.etCommentInput.setText("");
                binding.ratingBar.setRating(0);
                binding.etCommentInput.setError(null);

                // refresh the reviews
                viewModel.loadAccommodationReviews(accommodationID, binding);
            }
        });
    }


}
