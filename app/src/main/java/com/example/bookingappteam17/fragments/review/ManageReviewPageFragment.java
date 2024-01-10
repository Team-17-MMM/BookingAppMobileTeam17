package com.example.bookingappteam17.fragments.review;

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
import com.example.bookingappteam17.databinding.FragmentApproveAccommodationPageBinding;
import com.example.bookingappteam17.databinding.FragmentManageReviewPageBinding;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;
import com.example.bookingappteam17.services.review.IReviewService;

import java.util.ArrayList;
import java.util.List;

public class ManageReviewPageFragment extends Fragment {
    private ArrayList<ReportedReviewCardDTO> reviews = new ArrayList<>();
    private ManageReviewPageViewModel manageReviewPageViewModel;
    private ManageReviewListFragment reviewListFragment;
    private FragmentManageReviewPageBinding binding;
    private SharedPreferences sharedPreferences;
    private IReviewService reviewService = ClientUtils.reviewService;

    public static ManageReviewPageFragment newInstance() {
        return new ManageReviewPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentManageReviewPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        manageReviewPageViewModel = new ViewModelProvider(this).get(ManageReviewPageViewModel.class);

        // Initialize the list fragment
        reviewListFragment = new ManageReviewListFragment();

        // Set up the adapter
        ManageReviewListAdapter adapter = new ManageReviewListAdapter(getActivity(), reviews);
        reviewListFragment.setListAdapter(adapter);

        // Load data
        manageReviewPageViewModel.loadReviews();

        // Observe LiveData and update UI
        manageReviewPageViewModel.getReviewsLiveData().observe(getViewLifecycleOwner(), new Observer<List<ReportedReviewCardDTO>>() {
            @Override
            public void onChanged(List<ReportedReviewCardDTO> reportedReviewCardDTOS) {
                adapter.setReviews(reportedReviewCardDTOS);
            }
        });

        // Set up the fragment
        FragmentTransition.to(reviewListFragment, getActivity(), false, R.id.scroll_manage_review_list);

        return root;
    }
}
