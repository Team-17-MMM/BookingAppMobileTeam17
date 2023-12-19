package com.example.bookingappteam17.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.databinding.ActivityHostAccommodationsDetailsBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HostAccommodationDetailActivity extends AppCompatActivity {
    private ActivityHostAccommodationsDetailsBinding binding;
    private AccommodationDTO accommodationDTO;
//    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHostAccommodationsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        // Initialize osmdroid
//        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osmdroid", MODE_PRIVATE));
//
//        mapView = findViewById(R.id.mapView);
//        mapView.getController().setZoom(10.0);

        Long selectedAccommodation = getIntent().getLongExtra("selected_accommodation", 0);
        System.out.println(selectedAccommodation);

        Call<AccommodationDTO> call = ClientUtils.accommodationService.getAccommodation(selectedAccommodation);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    accommodationDTO = response.body();
                    // bind data from accommodationDTO to layout
                    bindData();
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

    }

    private void bindData() {
        if (accommodationDTO != null) {
            binding.accommodationNameEditText.setText(accommodationDTO.getName());
            binding.countryEditText.setText(accommodationDTO.getLocation().getCountry());
            binding.addressEditText.setText(accommodationDTO.getLocation().getAddress());
            binding.descriptionEditText.setText(accommodationDTO.getDescription());
            // check if confirmationType is "AUTO" or "MANUAL" and set it to corresponding radio button
            if (accommodationDTO.getConfirmationType().equals("AUTO")) {
                binding.autoRadioButton.setChecked(true);
            } else {
                binding.manualRadioButton.setChecked(true);
            }

        }
    }
    public void showAmenitiesDialog(View view) {
        // show options dialog with amenities from accommodationService.getAllAmenities()
        Call<List<String>> call = ClientUtils.accommodationService.getAllAmenities();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> amenities = response.body();
                if (amenities != null) {
                    String[] amenitiesArray = amenities.toArray(new String[0]);
                    boolean[] checkedAmenities = new boolean[amenitiesArray.length];
                    for (int i = 0; i < amenitiesArray.length; i++) {
                        checkedAmenities[i] = false;
                    }
                    // show dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(HostAccommodationDetailActivity.this);
                    builder.setTitle("Choose amenities");
                    builder.setMultiChoiceItems(amenitiesArray, checkedAmenities, (dialog, which, isChecked) -> {
                        checkedAmenities[which] = isChecked;
                    });
                    builder.setPositiveButton("OK", (dialog, which) -> {
                        // get selected amenities
                        List<String> selectedAmenities = new ArrayList<>();
                        for (int i = 0; i < checkedAmenities.length; i++) {
                            if (checkedAmenities[i]) {
                                selectedAmenities.add(amenitiesArray[i]);
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("Error", "Network request failed", t);
            }
        });
    }
}
