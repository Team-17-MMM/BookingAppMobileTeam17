package com.example.bookingappteam17.fragments.report;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.FragmentManageReviewPageBinding;
import com.example.bookingappteam17.databinding.FragmentManageUserReportsPageBinding;
import com.example.bookingappteam17.dto.user.UserReportDTO;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.fragments.review.ManageReviewListAdapter;
import com.example.bookingappteam17.fragments.review.ManageReviewListFragment;
import com.example.bookingappteam17.fragments.review.ManageReviewPageFragment;
import com.example.bookingappteam17.fragments.review.ManageReviewPageViewModel;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;
import com.example.bookingappteam17.services.review.IReviewService;

import java.util.ArrayList;
import java.util.List;

public class ManageUserReportsFragment extends Fragment {

    private ArrayList<UserReportDTO> reviews = new ArrayList<>();
    private ManageUserReportsPageViewModel manageReviewPageViewModel;
    private ManageUserReportsListFragment reviewListFragment;
    private FragmentManageUserReportsPageBinding binding;
    private SharedPreferences sharedPreferences;
    private IReviewService reviewService = ClientUtils.reviewService;

    public static ManageUserReportsFragment newInstance() {
        return new ManageUserReportsFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentManageUserReportsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        manageReviewPageViewModel = new ViewModelProvider(this).get(ManageUserReportsPageViewModel.class);

        // Initialize the list fragment
        reviewListFragment = new ManageUserReportsListFragment();

        // Set up the adapter
        ManageUserReportsListAdapter adapter = new ManageUserReportsListAdapter(getActivity(), reviews);
        reviewListFragment.setListAdapter(adapter);

        // Load data
        manageReviewPageViewModel.loadReviews();

        // Observe LiveData and update UI
        manageReviewPageViewModel.getReviewsLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserReportDTO>>() {
            @Override
            public void onChanged(List<UserReportDTO> reportedReviewCardDTOS) {
                adapter.setReviews(reportedReviewCardDTOS);
            }
        });

        // Set up the fragment
        FragmentTransition.to(reviewListFragment, getActivity(), false, R.id.scroll_manage_review_list_user);

        return root;
    }

}
