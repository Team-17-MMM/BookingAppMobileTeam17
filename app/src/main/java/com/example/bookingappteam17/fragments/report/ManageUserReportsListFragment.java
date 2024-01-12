package com.example.bookingappteam17.fragments.report;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.databinding.FragmentManageReviewListBinding;
import com.example.bookingappteam17.dto.user.UserReportDTO;
import com.example.bookingappteam17.fragments.review.ManageReviewListAdapter;
import com.example.bookingappteam17.fragments.review.ManageReviewListFragment;
import com.example.bookingappteam17.fragments.review.ManageReviewPageViewModel;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;

import java.util.ArrayList;
import java.util.List;

public class ManageUserReportsListFragment extends ListFragment {
    private ManageUserReportsListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private FragmentManageReviewListBinding binding;

    public static ManageReviewListFragment newInstance(ArrayList<UserReportDTO> reviews) {
        ManageReviewListFragment fragment = new ManageReviewListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, reviews);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentManageReviewListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ManageUserReportsPageViewModel viewModel = new ViewModelProvider(this).get(ManageUserReportsPageViewModel.class);
        adapter = new ManageUserReportsListAdapter(getActivity(), new ArrayList<>());
        setListAdapter(adapter);

        viewModel.getReviewsLiveData().observe(getViewLifecycleOwner(), new Observer<List<UserReportDTO>>() {
            @Override
            public void onChanged(List<UserReportDTO> reportedReviewCardDTOS) {
                adapter.clear();
                adapter.addAll(reportedReviewCardDTOS);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.loadReviews();


        return root;
    }
}