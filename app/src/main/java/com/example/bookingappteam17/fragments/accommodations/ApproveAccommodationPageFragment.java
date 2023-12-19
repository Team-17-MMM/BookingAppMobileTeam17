package com.example.bookingappteam17.fragments.accommodations;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.FragmentAccommodationsPageBinding;
import com.example.bookingappteam17.databinding.FragmentApproveAccommodationPageBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.services.IAccommodationService;

import java.util.ArrayList;

public class ApproveAccommodationPageFragment extends Fragment {
    public static ArrayList<AccommodationCardDTO> accommodations = new ArrayList<AccommodationCardDTO>();
    private ApproveAccommodationPageViewModel approveAccommodationsPageViewModel;
    private ApproveAccommodationListFragment accommodationListFragment;
    private FragmentApproveAccommodationPageBinding binding;
    private SharedPreferences sharedPreferences;
    private IAccommodationService accommodationService = ClientUtils.accommodationService;
    public static AccommodationPageFragment newInstance() {
        return new AccommodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        approveAccommodationsPageViewModel = new ViewModelProvider(this).get(ApproveAccommodationPageViewModel.class);

        binding = FragmentApproveAccommodationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareAccommodationList(accommodations);

        // Calls FragmentTransition.to to replace the layout with a ApproveAccommodationsListFragment.
        FragmentTransition.to(ApproveAccommodationListFragment.newInstance(accommodations), getActivity(), false, R.id.scroll_approve_accommodation_list);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareAccommodationList(ArrayList<AccommodationCardDTO> accommodations) {
        // TODO: Get accommodations from the server which are waiting for approval.

    }
}
