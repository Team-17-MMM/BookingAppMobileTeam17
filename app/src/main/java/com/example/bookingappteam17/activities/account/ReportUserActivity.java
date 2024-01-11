package com.example.bookingappteam17.activities.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.adapters.HostReviewAdapter;
import com.example.bookingappteam17.adapters.ReportUserAdapter;
import com.example.bookingappteam17.databinding.ActivityRateHostBinding;
import com.example.bookingappteam17.databinding.ActivityReportUserBinding;
import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.dto.user.UserInfoDTO;
import com.example.bookingappteam17.dto.user.UserReportDTO;
import com.example.bookingappteam17.viewmodel.review.RateHostViewModel;
import com.example.bookingappteam17.viewmodel.review.ReportUserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ReportUserActivity extends AppCompatActivity {

    ActivityReportUserBinding binding;
    private RecyclerView recyclerView;
    private ReportUserAdapter reviewAdapter;
    private ReportUserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReportUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.hostReviewsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(ReportUserViewModel.class);

        reviewAdapter = new ReportUserAdapter(this, getIntent().getLongExtra("user_id", 0));
        recyclerView.setAdapter(reviewAdapter);

        // Observe changes in hostReviewsLiveData
        viewModel.getHostReviewsLiveData().observe(this, new Observer<List<UserInfoDTO>>() {
            @Override
            public void onChanged(List<UserInfoDTO> hostReviews) {
                // Update the UI with the new list of reviews
                reviewAdapter.setReviews(hostReviews);
            }
        });

//        get hostUsername from intent
        String hostUsername = getIntent().getStringExtra("host_username");
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        Long id = sharedPreferences.getLong("userId", 0);

        String role = sharedPreferences.getString("role","");
        // Load host reviews when the activity is created
        viewModel.loadUserForReport(id, role);

    }
}