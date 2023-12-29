package com.example.bookingappteam17.fragments.accommodation;

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
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;

import java.util.ArrayList;
import java.util.List;

public class ApproveAccommodationListFragment extends ListFragment {

    private ApproveAccommodationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private FragmentApproveAccommodationListBinding binding;

    public static ApproveAccommodationListFragment newInstance(ArrayList<AccommodationCardDTO> accommodations) {
        ApproveAccommodationListFragment fragment = new ApproveAccommodationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, accommodations);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentApproveAccommodationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        ApproveAccommodationPageViewModel viewModel = new ViewModelProvider(this).get(ApproveAccommodationPageViewModel.class);

        // Set up the adapter
        adapter = new ApproveAccommodationListAdapter(getActivity(), new ArrayList<>());
        setListAdapter(adapter);

        // Observe LiveData and update UI
        viewModel.getAccommodationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccommodationCardDTO>>() {
            @Override
            public void onChanged(List<AccommodationCardDTO> accommodationCardDTOS) {
                adapter.clear();
                adapter.addAll(accommodationCardDTOS);
                adapter.notifyDataSetChanged();
            }
        });

        // Load data
        viewModel.loadAccommodations();

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
