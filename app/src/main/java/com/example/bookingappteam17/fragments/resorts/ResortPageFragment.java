package com.example.bookingappteam17.fragments.resorts;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.enums.ResortType;
import com.example.bookingappteam17.fragments.FilterFragment;
import com.example.bookingappteam17.model.Capacity;
import com.example.bookingappteam17.model.FilterData;
import com.example.bookingappteam17.model.Resort;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.bookingappteam17.databinding.FragmentResortsPageBinding;
import com.example.bookingappteam17.fragments.FragmentTransition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResortPageFragment extends Fragment implements FilterFragment.FilterListener{

    public static ArrayList<Resort> resorts = new ArrayList<Resort>();
    private ResortPageViewModel productsViewModel;
    private ResortListFragment resortListFragment;
    private FragmentResortsPageBinding binding;
    private FilterData filterData;
    public static ResortPageFragment newInstance() {
        return new ResortPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = new ViewModelProvider(this).get(ResortPageViewModel.class);
        resorts.clear();

        binding = FragmentResortsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareProductList(resorts);


        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            // Show the FilterFragment as a BottomSheetDialogFragment
            FilterFragment filterFragment = new FilterFragment();
            filterFragment.setFilterListener(ResortPageFragment.this); // Set the listener
            filterFragment.show(getChildFragmentManager(), filterFragment.getTag());
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

                // Update the data directly in the ResortListFragment's adapter
                if (resortListFragment != null) {
                    ResortListAdapter adapter = resortListFragment.getAdapter();
                    if (adapter != null) {
                        adapter.sort(new Comparator<Resort>() {
                            @Override
                            public int compare(Resort resort1, Resort resort2) {
                                int result = Double.compare(resort1.getPrice(), resort2.getPrice());
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

        resortListFragment = ResortListFragment.newInstance(resorts);
        FragmentTransition.to(resortListFragment, getActivity(), false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onFilterApplied(FilterData filterData) {
        this.filterData = filterData;

        // Apply the filters to the resorts list
        applyFilters();

        // Update the resorts in ResortListFragment
        if (resortListFragment != null) {
            resortListFragment.updateResorts(resorts);
        }
    }

    private void applyFilters() {
        resorts.clear();
        ArrayList<Resort> originalResorts = new ArrayList<Resort>();
        prepareProductList(originalResorts);

        for (Resort resort : originalResorts) {
            // Check if the city matches
            if (!filterData.getCity().isEmpty() && !resort.getCity().equalsIgnoreCase(filterData.getCity())) {
                continue;
            }

            // Check if the occupancy is within the range
            if (resort.getCapacity().getMinOccupancy() > filterData.getOccupancy() || resort.getCapacity().getMaxOccupancy() < filterData.getOccupancy()) {
                continue;
            }

            // Check if the price is within the range
            int price = resort.getPrice();
            if (price < filterData.getMinPrice() || price > filterData.getMaxPrice()) {
                continue;
            }

            // Check if the resort type matches
            if (filterData.getResortType() != null && !resort.getType().equals(filterData.getResortType())) {
                continue;
            }

            // Check if the amenities match
            if (!filterData.getAmenities().isEmpty() && !resort.getAmenities().containsAll(filterData.getAmenities())) {
                continue;
            }

            // If all checks passed, add the resort to the filtered list
            resorts.add(resort);
        }

        if (resortListFragment != null) {
            ResortListAdapter adapter = resortListFragment.getAdapter();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareProductList(ArrayList<Resort> products){
        products.add(new Resort(1L, "Grand Hotel Kopaonik", "The modern and comfortable Grand Hotel Kopaonik is located at an altitude of 1,770 meters in the very center of Kopaonik and offers a magnificent view of the Kopaonik National Park.","Kopaonik", R.drawable.hotel_grand_kop, 20, new ArrayList<>(), ResortType.APARTMENT, new ArrayList<>(), new Capacity(1,4), new ArrayList<>() ));
        products.add(new Resort(2L, "Krila Zlatibora", "The establishment Krila Zlatibora offers accommodation with free private parking and is located in Zlatibor, in the region of Central Serbia.","Zlatibor", R.drawable.zlatibor, 15,new ArrayList<>(), ResortType.APARTMENT, new ArrayList<>(), new Capacity(1,4), new ArrayList<>()));
        products.add(new Resort(3L, "Brvnara Miris Bora", "The object Brvnara Miris Bora is located in Šljivovica and offers a garden, barbecue facilities, and a terrace. All rooms have a kitchen, flat-screen TV with satellite channels, and a private bathroom.","Tara", R.drawable.tara, 35, new ArrayList<>(), ResortType.APARTMENT, new ArrayList<>(), new Capacity(1,4), new ArrayList<>()));
    }
}
