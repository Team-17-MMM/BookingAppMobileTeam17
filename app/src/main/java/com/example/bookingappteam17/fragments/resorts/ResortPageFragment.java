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
import com.example.bookingappteam17.model.Resort;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.example.bookingappteam17.databinding.FragmentResortsPageBinding;
import com.example.bookingappteam17.fragments.FragmentTransition;

import java.util.ArrayList;

public class ResortPageFragment extends Fragment {

    public static ArrayList<Resort> resorts = new ArrayList<Resort>();
    private ResortPageViewModel productsViewModel;
    private FragmentResortsPageBinding binding;

    public static ResortPageFragment newInstance() {
        return new ResortPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        productsViewModel = new ViewModelProvider(this).get(ResortPageViewModel.class);

        binding = FragmentResortsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareProductList(resorts);

        SearchView searchView = binding.searchText;
        productsViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Button btnFilters = binding.btnFilters;
//        btnFilters.setOnClickListener(v -> {
//            Log.i("ShopApp", "Bottom Sheet Dialog");
//            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.FullScreenBottomSheetDialog);
//            View dialogView = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null);
//            bottomSheetDialog.setContentView(dialogView);
//            bottomSheetDialog.show();
//        });

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

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setMessage("Change the sort option?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.v("ShopApp", (String) parent.getItemAtPosition(position));
                                ((TextView) parent.getChildAt(0)).setTextColor(Color.MAGENTA);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = dialog.create();
                alert.show();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });


        FragmentTransition.to(ResortListFragment.newInstance(resorts), getActivity(), false, R.id.scroll_products_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareProductList(ArrayList<Resort> products){
        products.add(new Resort(1L, "Grand Hotel Kopaonik", "\"@string/kopaonik_hotel_desc\"","Kopaonik", R.drawable.hotel_grand_kop));
        products.add(new Resort(2L, "\"@string/zlatibor_hotel\"", "\"@string/zlatibor_desc\"","Zlatibor", R.drawable.zlatibor));
        products.add(new Resort(3L, "\"@string/tara_hotel\"", "\"@string/tara_desc\"","Tara", R.drawable.tara));
    }
}
