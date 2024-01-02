package com.example.bookingappteam17.fragments.accommodation;

import android.content.SharedPreferences;
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

import com.example.bookingappteam17.databinding.FragmentAccommodationListBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;

import java.util.ArrayList;
import java.util.List;

public class AccommodationListFragment extends ListFragment {

    private AccommodationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private FragmentAccommodationListBinding binding;

    public static AccommodationListFragment newInstance(ArrayList<AccommodationCardDTO> accommodations) {
        AccommodationListFragment fragment = new AccommodationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, accommodations);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccommodationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        AccommodationPageViewModel viewModel = new ViewModelProvider(this).get(AccommodationPageViewModel.class);

        // Set up the adapter
        adapter = new AccommodationListAdapter(getActivity(), new ArrayList<>());
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

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
        viewModel.loadAccommodations(sharedPreferences.getString("username", ""));

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