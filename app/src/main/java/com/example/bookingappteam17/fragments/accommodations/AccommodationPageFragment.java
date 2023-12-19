package com.example.bookingappteam17.fragments.accommodations;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.HomeActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.fragments.FilterFragment;
import com.example.bookingappteam17.model.Accommodation;
import com.example.bookingappteam17.databinding.FragmentAccommodationsPageBinding;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.services.IAccommodationService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationPageFragment extends Fragment {

    public static ArrayList<AccommodationCardDTO> accommodations = new ArrayList<AccommodationCardDTO>();
    private AccommodationPageViewModel productsViewModel;
    private AccommodationListFragment accommodationListFragment;
    private FragmentAccommodationsPageBinding binding;
    private SharedPreferences sharedPreferences;
    private IAccommodationService accommodationService = ClientUtils.accommodationService;
    public static AccommodationPageFragment newInstance() {
        return new AccommodationPageFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        productsViewModel = new ViewModelProvider(this).get(AccommodationPageViewModel.class);
        // check if accommodations have same accommodationID and if they do, remove one of them
        for (int i = 0; i < accommodations.size(); i++) {
            for (int j = i + 1; j < accommodations.size(); j++) {
                if (accommodations.get(i).getAccommodationID().equals(accommodations.get(j).getAccommodationID())) {
                    accommodations.remove(j);
                }
            }
        }


        binding = FragmentAccommodationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        prepareProductList(accommodations);

        SearchView searchView = binding.searchText;
        productsViewModel.getText().observe(getViewLifecycleOwner(), searchView::setQueryHint);

        Button btnFilters = binding.btnFilters;
        btnFilters.setOnClickListener(v -> {
            // Show the FilterFragment as a BottomSheetDialogFragment
            FilterFragment filterFragment = new FilterFragment();
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

        accommodationListFragment = AccommodationListFragment.newInstance(accommodations);
        FragmentTransition.to(accommodationListFragment, getActivity(), false, R.id.scroll_products_list);

        return root;
    }





    private void prepareProductList(ArrayList<AccommodationCardDTO> products){
        String userRole = sharedPreferences.getString("role", "");
        Long userId = sharedPreferences.getLong("userId", 0L);
        String username = sharedPreferences.getString("username", "");

        // if userRole HOST, show just his accommodations
        if (userRole.equals("HOST")) {
            Call<HashSet<AccommodationCardDTO>> call = accommodationService.getHostAccommodationsCards(username);
            call.enqueue(new Callback<HashSet<AccommodationCardDTO>>() {
                             @Override
                             public void onResponse(Call<HashSet<AccommodationCardDTO>> call, Response<HashSet<AccommodationCardDTO>> response) {
                                 if (response.isSuccessful()) {
                                     HashSet<AccommodationCardDTO> accommodations = response.body();
                                     for (AccommodationCardDTO accommodationCardDTO : accommodations) {
                                         loadImage(accommodationCardDTO, products);
                                     }
                                 }
                             }

                             @Override
                             public void onFailure(Call<HashSet<AccommodationCardDTO>> call, Throwable t) {
                                 Log.d("Error", t.getMessage());
                             }
                         }
            );

        } else {
            Call<HashSet<AccommodationCardDTO>> call = accommodationService.getAccommodationsCards();
            call.enqueue(new Callback<HashSet<AccommodationCardDTO>>() {
                             @Override
                             public void onResponse(Call<HashSet<AccommodationCardDTO>> call, Response<HashSet<AccommodationCardDTO>> response) {
                                 if (response.isSuccessful()) {
                                     HashSet<AccommodationCardDTO> accommodations = response.body();
                                     for (AccommodationCardDTO accommodationCardDTO : accommodations) {
                                         loadImage(accommodationCardDTO, products);
                                     }
                                 }
                             }

                             @Override
                             public void onFailure(Call<HashSet<AccommodationCardDTO>> call, Throwable t) {
                                 Log.d("Error", t.getMessage());
                             }
                         }
            );
        }

//        products.add(new Accommodation(1L, "Grand Hotel Kopaonik", "The modern and comfortable Grand Hotel Kopaonik is located at an altitude of 1,770 meters in the very center of Kopaonik and offers a magnificent view of the Kopaonik National Park.","Kopaonik", R.drawable.hotel_grand_kop, 2500, 1L));
//        products.add(new Accommodation(2L, "Krila Zlatibora", "The establishment Krila Zlatibora offers accommodation with free private parking and is located in Zlatibor, in the region of Central Serbia.","Zlatibor", R.drawable.zlatibor, 1800, 1L));
//        products.add(new Accommodation(3L, "Brvnara Miris Bora", "The object Brvnara Miris Bora is located in Šljivovica and offers a garden, barbecue facilities, and a terrace. All rooms have a kitchen, flat-screen TV with satellite channels, and a private bathroom.","Tara", R.drawable.tara, 3400, 1L));
    }

    private void loadImage(AccommodationCardDTO accommodationCardDTO, ArrayList<AccommodationCardDTO> products) {
        Call<ResponseBody> callImage = accommodationService.getAccommodationImage(accommodationCardDTO.getAccommodationID());
        callImage.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> callImage, Response<ResponseBody> responseImage) {
                if (responseImage.isSuccessful()) {
                    Bitmap bmp = BitmapFactory.decodeStream(responseImage.body().byteStream());
                    // Use the bitmap as needed, for example set it to an ImageView
                    accommodationCardDTO.setImage(bmp);
                    products.add(accommodationCardDTO);
                } else {
                    Log.d("Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Error", "Failed to get image", t);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
//        accommodations.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
//        accommodations.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
//        prepareProductList(accommodations);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}

