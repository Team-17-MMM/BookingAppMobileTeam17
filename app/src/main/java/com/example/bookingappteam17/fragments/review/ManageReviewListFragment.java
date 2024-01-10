package com.example.bookingappteam17.fragments.review;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.databinding.FragmentApproveAccommodationListBinding;
import com.example.bookingappteam17.databinding.FragmentManageReviewListBinding;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;

import java.util.ArrayList;
import java.util.List;

public class ManageReviewListFragment extends ListFragment {
    private ManageReviewListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private FragmentManageReviewListBinding binding;

    public static ManageReviewListFragment newInstance(ArrayList<ReportedReviewCardDTO> reviews) {
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
        ManageReviewPageViewModel viewModel = new ViewModelProvider(this).get(ManageReviewPageViewModel.class);
        adapter = new ManageReviewListAdapter(getActivity(), new ArrayList<>());
        setListAdapter(adapter);

        viewModel.getReviewsLiveData().observe(getViewLifecycleOwner(), new Observer<List<ReportedReviewCardDTO>>() {
            @Override
            public void onChanged(List<ReportedReviewCardDTO> reportedReviewCardDTOS) {
                adapter.clear();
                adapter.addAll(reportedReviewCardDTOS);
                adapter.notifyDataSetChanged();
            }
        });

        viewModel.loadReviews();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // TODO: Handle the click on item at 'position'
    }
}
