package com.example.bookingappteam17.fragments.reservation;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.databinding.FragmentAccommodationListBinding;
import com.example.bookingappteam17.databinding.FragmentReservationListBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.reservation.ReservationInfoDTO;
import com.example.bookingappteam17.fragments.accommodation.AccommodationListAdapter;
import com.example.bookingappteam17.fragments.accommodation.AccommodationPageViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ReservationsListFragment extends ListFragment {
    private List<ReservationInfoDTO> filteredReservations;
    private ReservationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private FragmentReservationListBinding binding;
    private ReservationViewModel viewModel;
    public ReservationListAdapter getAdapter(){
        return adapter;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentReservationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(ReservationViewModel.class);

        // Set up the adapter
        adapter = new ReservationListAdapter(getActivity(), new ArrayList<>(), this);
        setListAdapter(adapter);

        // Observe LiveData and update UI
        viewModel.getReservationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<ReservationInfoDTO>>() {
            @Override
            public void onChanged(List<ReservationInfoDTO> reservationInfoDTOS) {
                adapter.clear();
                adapter.addAll(reservationInfoDTOS);
                adapter.notifyDataSetChanged();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
        viewModel.loadReservations(sharedPreferences.getLong("userId", 0),sharedPreferences.getString("role", ""));

        return root;
    }

    public void reloadReservations() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        // Load reservations again
        viewModel.loadReservations(sharedPreferences.getLong("userId", 0), sharedPreferences.getString("role", ""));
    }

    public void updateReservationList(List<ReservationInfoDTO> reservations){
        viewModel.updateReservationList(reservations);
    }

    public List<ReservationInfoDTO> getReservationList(){
        List<ReservationInfoDTO> reservationInfoDTOS = viewModel.getReservationsLiveData().getValue();
        return viewModel.getReservationsLiveData().getValue();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
