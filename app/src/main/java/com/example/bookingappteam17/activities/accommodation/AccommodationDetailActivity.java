package com.example.bookingappteam17.activities.accommodation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.review.RateHostActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityAccommodationsDetailsBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.notification.EnabledNotificationsDTO;
import com.example.bookingappteam17.enums.accommodation.AccommodationType;
import com.example.bookingappteam17.enums.accommodation.Amenity;
import com.example.bookingappteam17.fragments.reservation.ReservationFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationDetailActivity extends AppCompatActivity {
    private boolean isPermissions = true;
    private String [] permissions = {
            android.Manifest.permission.INTERNET
    };

    private Long userID;
    private AccommodationDTO accommodation;
    private Bitmap image;
    private Uri imageUri = null;

    private static final int REQUEST_PERMISSIONS = 200;
    private ActivityAccommodationsDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = null;
        binding = ActivityAccommodationsDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Long selectedAccommodationID = getIntent().getLongExtra("selected_accommodation", 0);
        userID = getIntent().getLongExtra("user_id",0);

        Call<AccommodationDTO> call = ClientUtils.accommodationService.getAccommodation(selectedAccommodationID);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    accommodation = response.body();
                    // bind data from accommodationDTO to layout
                    bindData(view);
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

        Button btnFavorite = binding.accommodationDetailsAddFavorite;
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");
        if(role.equals("GUEST")){
            btnFavorite.setVisibility(View.VISIBLE);
        }
        btnFavorite.setOnClickListener(v -> {
            Log.d("TAG", "onFailure: " + "krmacica");
            this.addAccommodationToFavorite();
        });

        // rate host activity
        Button btnRateHost = binding.rateHostButton;
        btnRateHost.setOnClickListener(v -> {
            Intent intent = new Intent(this, RateHostActivity.class);
            intent.putExtra("user_id", userID);
            intent.putExtra("host_username", accommodation.getOwner().getUsername());
            intent.putExtra("host_id", accommodation.getOwner().getUserID());
            startActivity(intent);
        });

    }

    private void addAccommodationToFavorite(){
        Call<EnabledNotificationsDTO> call = ClientUtils.accommodationService.addToFavorite(this.accommodation.getAccommodationID(), this.userID);

        call.enqueue(new Callback<EnabledNotificationsDTO>() {
            @Override
            public void onResponse(Call<EnabledNotificationsDTO> call, Response<EnabledNotificationsDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("Error", "Network request failed" + "proslo");
                }
            }

            @Override
            public void onFailure(Call<EnabledNotificationsDTO> call, Throwable t) {
                Log.e("Error", "Network request failed", t);
            }
        });
    }

    private void bindData(View view){
        if (accommodation != null) {
            loadOldImage(accommodation.getAccommodationID());

            binding.countryEditText.setText(accommodation.getLocation().getCountry());
            binding.addressEditText.setText(accommodation.getLocation().getAddress());
            binding.accommodationDetailsTitle.setText(accommodation.getName());
            binding.accommodationDetailsDescription.setText(accommodation.getDescription());
            binding.minPersonsEditText.setText(String.valueOf(accommodation.getCapacity().getMinGuests()));
            binding.maxPersonsEditText.setText(String.valueOf(accommodation.getCapacity().getMaxGuests()));

            bindAccommodationType(view);

            createAmenitiesCheckboxes(view);

            //set map view to location
            Button btnReservation = binding.accommodationDetailsReservationButton;
            btnReservation.setOnClickListener(v -> {
                ReservationFragment reservationFragment = new ReservationFragment(accommodation,userID);
                reservationFragment.show(getSupportFragmentManager().beginTransaction(), "ReservationFragmentTag");
            });

        }
    }

    private void createAmenitiesCheckboxes(View view){
        for (Amenity amenity : accommodation.getAmenities()) {
            CheckBox checkBox = new CheckBox(view.getContext());
            checkBox.setText(amenity.name());
            checkBox.setChecked(true);
            checkBox.setEnabled(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                checkBox.setButtonTintList(ContextCompat.getColorStateList(view.getContext(), R.color.blue));
            }

            GridLayout.Spec rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Row weight
            GridLayout.Spec colSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Column weight

            GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;


            checkBox.setLayoutParams(params);

            binding.checkboxContainer.addView(checkBox);
        }
    }

    private void bindAccommodationType(View view){
        if (accommodation != null){
            if(accommodation.getAccommodationType().contains(AccommodationType.STUDIO)){
                binding.checkboxStudio.setChecked(true);
                binding.checkboxStudio.setButtonTintList(ContextCompat.getColorStateList(view.getContext(), R.color.blue));
            }
            if(accommodation.getAccommodationType().contains(AccommodationType.APARTMENT)){
                binding.checkboxApartment.setChecked(true);
                binding.checkboxApartment.setButtonTintList(ContextCompat.getColorStateList(view.getContext(), R.color.blue));
            }
            if(accommodation.getAccommodationType().contains(AccommodationType.ROOM)){
                binding.checkboxRoom.setChecked(true);
                binding.checkboxRoom.setButtonTintList(ContextCompat.getColorStateList(view.getContext(), R.color.blue));
            }
        }
    }

    private void loadOldImage(Long id) {
        Call<ResponseBody> callImage = ClientUtils.accommodationService.getAccommodationImage(id);
        callImage.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> callImage, Response<ResponseBody> responseImage) {
                if (responseImage.isSuccessful()) {
                    try {
                        // Convert ResponseBody to InputStream
                        InputStream inputStream = responseImage.body().byteStream();

                        // Save image to cache with a specific filename
                        File outputDir = getCacheDir();
                        File outputFile = new File(outputDir, "temp_image.jpg");
                        try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                            byte[] buffer = new byte[4096];
                            int bytesRead;

                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }

                        // Set imageUri to the cache image
                        imageUri = Uri.fromFile(outputFile);
                        binding.accommodationDetailsImage.setImageURI(imageUri);


                    } catch (IOException e) {
                        Log.e("Error", "Error saving image to cache: " + e.getMessage());
                    }
                } else {
                    Log.d("Error", "Response not successful");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error", "Failed to get image", t);
            }
        });
    }
}
