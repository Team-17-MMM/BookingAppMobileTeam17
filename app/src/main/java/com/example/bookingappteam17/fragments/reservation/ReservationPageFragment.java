package com.example.bookingappteam17.fragments.reservation;

import static com.example.bookingappteam17.clients.ClientUtils.reservationService;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.databinding.FragmentReservationPageBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.reservation.ReservationFilterRequestDTO;
import com.example.bookingappteam17.dto.reservation.ReservationInfoDTO;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.fragments.accommodation.AccommodationFilterFragment;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationPageFragment extends Fragment {
    private ArrayList<AccommodationCardDTO> accommodations = new ArrayList<>();
    private ReservationsListFragment reservationsListFragment;
    private FragmentReservationPageBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        binding = FragmentReservationPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        reservationsListFragment = new ReservationsListFragment();

        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            ReservationFilterFragment reservationFilterFragment = new ReservationFilterFragment(reservationsListFragment,sharedPreferences.getLong("userId", 0));
            reservationFilterFragment.show(getChildFragmentManager(), reservationFilterFragment.getTag());
        });

        // Calls FragmentTransition.to to replace the layout with a ReservationListFragment.
        FragmentTransition.to(reservationsListFragment, getActivity(), false, R.id.scroll_reservations_list);

        return root;
    }


}
