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
import com.example.bookingappteam17.adapters.HostReviewAdapter;
import com.example.bookingappteam17.databinding.ActivityRateHostBinding;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.dto.user.UserInfoDTO;
import com.example.bookingappteam17.viewmodel.review.RateHostViewModel;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class RateHostActivity extends AppCompatActivity {
    ActivityRateHostBinding binding;
    private RecyclerView recyclerView;
    private HostReviewAdapter reviewAdapter;
    private RateHostViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRateHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.hostReviewsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(RateHostViewModel.class);

        reviewAdapter = new HostReviewAdapter(this, getIntent().getLongExtra("user_id", 0), getIntent().getLongExtra("host_id", 0));
        recyclerView.setAdapter(reviewAdapter);

        // Observe changes in hostReviewsLiveData
        viewModel.getHostReviewsLiveData().observe(this, new Observer<List<HostReviewDTO>>() {
            @Override
            public void onChanged(List<HostReviewDTO> hostReviews) {
                // Update the UI with the new list of reviews
                reviewAdapter.setReviews(hostReviews);
            }
        });

//        get hostUsername from intent
        String hostUsername = getIntent().getStringExtra("host_username");

        // Load host reviews when the activity is created
        viewModel.loadHostReviews(hostUsername, binding.ratingBarHost);

        viewModel.loadUserInfo(getIntent().getStringExtra("host_username"), binding);

        viewModel.setCommentVisibility(getIntent().getLongExtra("host_id", 0), getIntent().getLongExtra("user_id", 0), binding);

        if (getIntent().getLongExtra("user_id", 0) == 0) {
            binding.commentSection.setVisibility(View.GONE);
        }

        Button submitReviewButton = binding.btnSubmitComment;
        submitReviewButton.setOnClickListener(v -> {
            if (binding.ratingBar.getRating() == 0 || Objects.requireNonNull(binding.etCommentInput.getText()).toString().equals("")) {
                binding.etCommentInput.setError("Please enter a comment and rating");
                binding.ratingBar.setOutlineSpotShadowColor(getResources().getColor(R.color.red));
            } else {
                HostReviewDTO review = new HostReviewDTO();
                review.setComment(binding.etCommentInput.getText().toString());
                review.setRating((int) binding.ratingBar.getRating());
                review.setReviewer(getIntent().getLongExtra("user_id", 0));
                review.setReviewedHost(getIntent().getLongExtra("host_id", 0));
                review.setReviewDate(LocalDate.now().toString());
                viewModel.submitHostReview(review);

                // clear the comment input field and rating bar and close error messages
                binding.etCommentInput.setText("");
                binding.ratingBar.setRating(0);
                binding.etCommentInput.setError(null);

                // refresh the reviews
                viewModel.loadHostReviews(hostUsername, binding.ratingBarHost);
            }
        });
    }
}
