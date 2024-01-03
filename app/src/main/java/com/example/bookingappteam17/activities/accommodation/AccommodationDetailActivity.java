package com.example.bookingappteam17.activities.accommodation;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityAccommodationsDetailsBinding;
import com.example.bookingappteam17.databinding.ActivityHostAccommodationsDetailsBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.fragments.reservation.ReservationFragment;
import com.example.bookingappteam17.viewmodel.SharedViewModel;

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
        setContentView(binding.getRoot());
        Long selectedAccommodationID = getIntent().getLongExtra("selected_accommodation", 0);
        userID = getIntent().getLongExtra("user_id",0);

        Call<AccommodationDTO> call = ClientUtils.accommodationService.getAccommodation(selectedAccommodationID);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    accommodation = response.body();
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

    private void bindData(){
        if (accommodation != null) {
            loadOldImage(accommodation.getAccommodationID());


            binding.accommodationDetailsTitle.setText(accommodation.getName());
            binding.accommodationDetailsDescription.setText(accommodation.getDescription());
            binding.accommodationDetailsPrice.setText(String.valueOf(accommodation.getPrice()));
            Button btnReservation = binding.accommodationDetailsReservationButton;
            btnReservation.setOnClickListener(v -> {
                ReservationFragment reservationFragment = new ReservationFragment(accommodation, userID);
                reservationFragment.show(getSupportFragmentManager(), "ReservationFragmentTag");
            });

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
