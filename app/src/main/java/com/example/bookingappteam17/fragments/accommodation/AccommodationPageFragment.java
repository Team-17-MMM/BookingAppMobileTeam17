package com.example.bookingappteam17.fragments.accommodation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.accommodation.AccommodationReportActivity;
import com.example.bookingappteam17.activities.accommodation.RegisterAccommodationActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.databinding.FragmentAccommodationsPageBinding;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.services.accommodation.IAccommodationService;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class AccommodationPageFragment extends Fragment {

    private ArrayList<AccommodationCardDTO> accommodations = new ArrayList<>();
    private AccommodationPageViewModel accommodationsPageViewModel;
    private AccommodationListFragment accommodationListFragment;
    private FragmentAccommodationsPageBinding binding;
    private SharedPreferences sharedPreferences;
    private IAccommodationService accommodationService = ClientUtils.accommodationService;

    public static AccommodationPageFragment newInstance() {
        return new AccommodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String role = sharedPreferences.getString("role","");
        binding = FragmentAccommodationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize ViewModel
        accommodationsPageViewModel = new ViewModelProvider(this).get(AccommodationPageViewModel.class);

        // Initialize the list fragment
        accommodationListFragment = new AccommodationListFragment();

        // Set up the adapter
        AccommodationListAdapter adapter = new AccommodationListAdapter(getActivity(), accommodations);
        accommodationListFragment.setListAdapter(adapter);

        // Load data
        accommodationsPageViewModel.loadAccommodations(username,role);

        // Observe LiveData and update UI
        accommodationsPageViewModel.getAccommodationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<AccommodationCardDTO>>() {
            @Override
            public void onChanged(List<AccommodationCardDTO> accommodationCardDTOS) {
                adapter.setAccommodations(accommodationCardDTOS);
            }
        });

        // buttons
        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            // Show the FilterFragment as a BottomSheetDialogFragment
            AccommodationFilterFragment accommodationFilterFragment = new AccommodationFilterFragment(accommodationListFragment.getAdapter());
            accommodationFilterFragment.setOnDismissListener(() -> {
                adapter.notifyDataSetChanged();
            });
            accommodationFilterFragment.show(getChildFragmentManager(), accommodationFilterFragment.getTag());
        });


        Button btnAddAccommodation = binding.btnAddAccommodation;
        btnAddAccommodation.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), RegisterAccommodationActivity.class);
            startActivity(intent);
        });

        Button btnGetReport = binding.btnGetReport;
        btnGetReport.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), AccommodationReportActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        Spinner spinner = binding.btnSort;
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.sort_array));
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                boolean isAscending = (position == 0);

                // Update the data directly in the AccommodationListFragment's adapter
                if (accommodationListFragment != null) {
                    AccommodationListAdapter adapter = accommodationListFragment.getAdapter();
                    if (adapter != null) {
                        adapter.sort(new Comparator<AccommodationCardDTO>() {
                            @Override
                            public int compare(AccommodationCardDTO accommodation1, AccommodationCardDTO accommodation2) {
//                                int result = Double.compare(accommodation1.getPrice(), accommodation2.getPrice());
                                int result = Double.compare(1000, 1000);

                                return isAscending ? result : -result;
                            }
                        });

                        // Notify the adapter to update the UI
                        adapter.notifyDataSetChanged();
                    }
                }
            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // Calls FragmentTransition.to to replace the layout with a AccommodationsListFragment.
        FragmentTransition.to(accommodationListFragment, getActivity(), false, R.id.scroll_products_list);

        return root;
    }
}

