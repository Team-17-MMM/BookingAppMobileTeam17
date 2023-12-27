package com.example.bookingappteam17.fragments.accommodations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.services.IAccommodationService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveAccommodationPageFragment extends Fragment {

    private ArrayList<AccommodationCardDTO> accommodations = new ArrayList<>();
    private ApproveAccommodationPageViewModel approveAccommodationsPageViewModel;
    private ApproveAccommodationListFragment accommodationListFragment;
    private FragmentApproveAccommodationPageBinding binding;
    private SharedPreferences sharedPreferences;
    private IAccommodationService accommodationService = ClientUtils.accommodationService;

    public static ApproveAccommodationPageFragment newInstance() {
        return new ApproveAccommodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentApproveAccommodationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        approveAccommodationsPageViewModel = new ViewModelProvider(this).get(ApproveAccommodationPageViewModel.class);

        // Initialize the list fragment
        accommodationListFragment = new ApproveAccommodationListFragment();

        // Set up the adapter
        ApproveAccommodationListAdapter adapter = new ApproveAccommodationListAdapter(getActivity(), accommodations);
        accommodationListFragment.setListAdapter(adapter);

        // Load data
        approveAccommodationsPageViewModel.loadAccommodations();

        // Observe LiveData and update UI
        approveAccommodationsPageViewModel.getAccommodationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccommodationCardDTO>>() {
            @Override
            public void onChanged(List<AccommodationCardDTO> accommodationCardDTOS) {
                adapter.setAccommodations(accommodationCardDTOS);
            }
        });

        // Calls FragmentTransition.to to replace the layout with a ApproveAccommodationsListFragment.
        FragmentTransition.to(accommodationListFragment, getActivity(), false, R.id.scroll_approve_accommodation_list);

        return root;
    }
}
