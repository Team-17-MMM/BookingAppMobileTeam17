package com.example.bookingappteam17.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.databinding.ActivityHostAccommodationsDetailsBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationUpdateDTO;
import com.example.bookingappteam17.enums.AccommodationType;
import com.example.bookingappteam17.enums.Amenity;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public class HostAccommodationDetailActivity extends AppCompatActivity {
    private ActivityHostAccommodationsDetailsBinding binding;
    private AccommodationDTO accommodationDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHostAccommodationsDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> {
            updateAccommodation();
        });

        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        uploadImageButton.setOnClickListener(v -> {
            chooseImage();
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
            binding.minPersonsEditText.setText(String.valueOf(accommodationDTO.getCapacity().getMinGuests()));
            binding.maxPersonsEditText.setText(String.valueOf(accommodationDTO.getCapacity().getMaxGuests()));
            //set map view to location
            GeoPoint location = new GeoPoint(accommodationDTO.getLocation().getLatitude(), accommodationDTO.getLocation().getLongitude());
            binding.mapView.getController().setZoom(13.0);
            binding.mapView.getController().setCenter(location);
            // add marker to map view
            Marker marker = new Marker(binding.mapView);
            marker.setPosition(location);
            binding.mapView.getOverlays().add(marker);
            binding.mapView.invalidate();

            binding.mapView.getOverlays().add(new MapEventsOverlay(new MapEventsReceiver() {
                @Override
                public boolean singleTapConfirmedHelper(GeoPoint geoPoint) {
                    return false;
                }

                @Override
                public boolean longPressHelper(GeoPoint geoPoint) {
                    handleLongPress(geoPoint);
                    return true;
                }
            }));
        }
    }

    private void handleLongPress(GeoPoint geoPoint) {
        // set location to geoPoint
        accommodationDTO.getLocation().setLatitude(geoPoint.getLatitude());
        accommodationDTO.getLocation().setLongitude(geoPoint.getLongitude());
        // set marker to geoPoint
        Marker marker = new Marker(binding.mapView);
        marker.setPosition(geoPoint);
        binding.mapView.getOverlays().clear();
        binding.mapView.getOverlays().add(marker);
        binding.mapView.getController().setZoom(13.0);
        binding.mapView.getController().setCenter(geoPoint);
        binding.mapView.invalidate();
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
                        // find amenity with same name as amenityArray[i]
                        Amenity amenity = Amenity.valueOf(amenitiesArray[i]);
                        // if accommodationDTO contains amenity, set checkedAmenities[i] to true
                        checkedAmenities[i] = accommodationDTO.getAmenities().contains(amenity);
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
                        // update accommodationDTO amenities
                        HashSet<Amenity> amenitiesSet = new HashSet<>();
                        for (String amenity : selectedAmenities) {
                            amenitiesSet.add(Amenity.valueOf(amenity));
                        }
                        accommodationDTO.setAmenities(amenitiesSet);
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

    public void showAccommodationTypeDialog(View view) {
        // show options dialog with accommodation types from accommodationService.getAllAccommodationTypes()
        Call<List<String>> call = ClientUtils.accommodationService.getAllAccommodationTypes();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                List<String> accommodationTypes = response.body();
                if (accommodationTypes != null) {
                    String[] accommodationTypesArray = accommodationTypes.toArray(new String[0]);
                    boolean[] checkedAccommodationTypes = new boolean[accommodationTypesArray.length];
                    for (int i = 0; i < accommodationTypesArray.length; i++) {
                        // find accommodationType with same name as accommodationTypesArray[i]
                        AccommodationType accommodationType = AccommodationType.valueOf(accommodationTypesArray[i]);
                        // if accommodationDTO contains accommodationType, set checkedAccommodationTypes[i] to true
                        checkedAccommodationTypes[i] = accommodationDTO.getAccommodationType().contains(accommodationType);
                    }
                    // show dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(HostAccommodationDetailActivity.this);
                    builder.setTitle("Choose accommodation types");
                    builder.setMultiChoiceItems(accommodationTypesArray, checkedAccommodationTypes, (dialog, which, isChecked) -> {
                        checkedAccommodationTypes[which] = isChecked;
                    });
                    builder.setPositiveButton("OK", (dialog, which) -> {
                        // get selected accommodation types
                        List<String> selectedAccommodationTypes = new ArrayList<>();
                        for (int i = 0; i < checkedAccommodationTypes.length; i++) {
                            if (checkedAccommodationTypes[i]) {
                                selectedAccommodationTypes.add(accommodationTypesArray[i]);
                            }
                        }
                        // update accommodationDTO accommodation types
                        HashSet<AccommodationType> accommodationTypesSet = new HashSet<>();
                        for (String accommodationType : selectedAccommodationTypes) {
                            accommodationTypesSet.add(AccommodationType.valueOf(accommodationType));
                        }
                        accommodationDTO.setAccommodationType(accommodationTypesSet);
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

    public void updateAccommodation() {
        // update accommodationDTO with data from layout
        accommodationDTO.setName(binding.accommodationNameEditText.getText().toString());
        accommodationDTO.getLocation().setCountry(binding.countryEditText.getText().toString());
        accommodationDTO.getLocation().setAddress(binding.addressEditText.getText().toString());
        accommodationDTO.setDescription(binding.descriptionEditText.getText().toString());
        if (binding.autoRadioButton.isChecked()) {
            accommodationDTO.setConfirmationType("AUTO");
        } else {
            accommodationDTO.setConfirmationType("MANUAL");
        }
        accommodationDTO.getCapacity().setMinGuests((int) Long.parseLong(binding.minPersonsEditText.getText().toString()));
        accommodationDTO.getCapacity().setMaxGuests((int) Long.parseLong(binding.maxPersonsEditText.getText().toString()));

        // update accommodation
        Call<AccommodationUpdateDTO> call = ClientUtils.accommodationService.updateAccommodation(accommodationDTO, accommodationDTO.getAccommodationID());
        call.enqueue(new Callback<AccommodationUpdateDTO>() {
            @Override
            public void onResponse(Call<AccommodationUpdateDTO> call, Response<AccommodationUpdateDTO> response) {
                if (response.isSuccessful()) {
                    // show success dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(HostAccommodationDetailActivity.this);
                    builder.setTitle("Success");
                    builder.setMessage("Accommodation updated successfully");
                    builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }

            @Override
            public void onFailure(Call<AccommodationUpdateDTO> call, Throwable t) {
                Log.e("Error", "Network request failed", t);
            }
        });
    }


    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            // Now you can call the uploadImage method with the selected imageUri
            uploadImage(accommodationDTO.getAccommodationID(), imageUri);
        }
    }

    private void uploadImage(Long accommodationId, Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            if (inputStream != null) {
                // Create a temporary file to write the image data
                File file = createTempImageFile(inputStream);

                // Create RequestBody and MultipartBody.Part for the image file
                RequestBody requestFile = RequestBody.create(
                        MediaType.parse(getContentResolver().getType(imageUri)),
                        file
                );
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                // Call the API to upload the image
                Call<Void> call = ClientUtils.accommodationService.uploadAccommodationPicture(accommodationId, imagePart);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        // show success dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(HostAccommodationDetailActivity.this);
                        builder.setTitle("Success");
                        builder.setMessage("Image uploaded successfully");
                        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Handle the failure, show an error message, etc.
                        Log.e("Error", "Image upload failed", t);
                    }
                });
            } else {
                Log.e("Error", "Failed to open InputStream for selected image");
            }
        } catch (FileNotFoundException e) {
            Log.e("Error", "File not found: " + e.getMessage());
        }
    }

    private File createTempImageFile(InputStream inputStream) {
        try {
            File outputDir = getCacheDir();
            File outputFile = File.createTempFile("temp_image", null, outputDir);

            try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return outputFile;
        } catch (IOException e) {
            Log.e("Error", "Error creating temporary image file: " + e.getMessage());
            return null;
        }
    }

}