package com.example.bookingappteam17.activities.review;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam17.adapters.HostReviewAdapter;
import com.example.bookingappteam17.databinding.ActivityRateHostBinding;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.viewmodel.review.RateHostViewModel;

import java.util.List;

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

        reviewAdapter = new HostReviewAdapter(this);
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
        viewModel.loadHostReviews(hostUsername);
    }
}
