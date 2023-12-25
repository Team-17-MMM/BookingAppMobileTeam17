package com.example.bookingappteam17.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.UserInfoDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.CapacityDTO;
import com.example.bookingappteam17.enums.AccommodationType;
import com.example.bookingappteam17.enums.Amenity;
import com.example.bookingappteam17.model.Location;
import com.example.bookingappteam17.model.User;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAccommodationActivity extends AppCompatActivity {

    private static final String USER_PREFS_KEY = "user_prefs";
    private static final String USER_ID_KEY = "userId";
    private AccommodationDTO accommodationDTO = new AccommodationDTO();
    private Uri imageUri = null;

    private User owner = null;


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedPreferences = getSharedPreferences(USER_PREFS_KEY, Context.MODE_PRIVATE);
        loadUserData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_reservation);

        Button buttonLogin = findViewById(R.id.updateButton);
        accommodationDTO.setLocation(new Location());
        GeoPoint location = new GeoPoint(45.0, 22.0);
        MapView mapView = findViewById(R.id.mapView);
        mapView.getController().setZoom(13.0);
        mapView.getController().setCenter(location);
        mapView.invalidate();

        mapView.getOverlays().add(new MapEventsOverlay(new MapEventsReceiver() {
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

        buttonLogin.setOnClickListener(v -> handleRegistration());


        Button uploadImageButton = findViewById(R.id.uploadImageButton);
        uploadImageButton.setOnClickListener(v -> {
            chooseImage();
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
            this.imageUri = imageUri;
        }
    }

    private void handleLongPress(GeoPoint geoPoint) {
        // set location to geoPoint
        accommodationDTO.getLocation().setLatitude(geoPoint.getLatitude());
        accommodationDTO.getLocation().setLongitude(geoPoint.getLongitude());
        // set marker to geoPoint
        MapView mapView = findViewById(R.id.mapView);
        Marker marker = new Marker(mapView);
        marker.setPosition(geoPoint);
        mapView.getOverlays().clear();
        mapView.getOverlays().add(marker);
        mapView.getController().setZoom(13.0);
        mapView.getController().setCenter(geoPoint);
        mapView.invalidate();

        EditText editTextLatitude = findViewById(R.id.latitudeEditText);
        EditText editTextLongitude = findViewById(R.id.longitudeEditText);

        editTextLongitude.setText(Double.toString(geoPoint.getLongitude()));
        editTextLatitude.setText(Double.toString(geoPoint.getLatitude()));
    }

    public void showRegistrationAmenitiesDialog(View view) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAccommodationActivity.this);
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


    public void showRegistrationAccommodationTypeDialog(View view) {
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAccommodationActivity.this);
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


    private void handleRegistration() {
        if(imageUri == null){
            return;
        }
        EditText editTextRegisterName = findViewById(R.id.accommodationNameEditText);
        EditText editTextAddress = findViewById(R.id.addressEditText);
        EditText editTextCountry = findViewById(R.id.countryEditText);
        EditText editTextDescription = findViewById(R.id.descriptionEditText);
        EditText editTextMinPersons = findViewById(R.id.minPersonsEditText);
        EditText editTextMaxPersons = findViewById(R.id.maxPersonsEditText);
        EditText editTextLatitude = findViewById(R.id.latitudeEditText);
        EditText editTextLongitude = findViewById(R.id.longitudeEditText);
        EditText editTextCancelingDays = findViewById(R.id.cancelingDaysEditText);
        RadioGroup radioGroupConfirmation = findViewById(R.id.roleRadioGroup);
        RadioGroup radioGroupPricing = findViewById(R.id.roleRadioGroupPricing);


        String name = editTextRegisterName.getText().toString();
        String address = editTextAddress.getText().toString();
        String country = editTextCountry.getText().toString();
        String description = editTextDescription.getText().toString();
        String maxPersons = editTextMaxPersons.getText().toString();
        String minPersons = editTextMinPersons.getText().toString();
        String latitude = editTextLatitude.getText().toString();
        String longitude = editTextLongitude.getText().toString();
        String cancelingDays = editTextCancelingDays.getText().toString();
        int selectedRadioButtonConfirmationId = radioGroupConfirmation.getCheckedRadioButtonId();
        int selectedRadioButtonPricingId = radioGroupPricing.getCheckedRadioButtonId();

        if (name.isEmpty()) {
            editTextRegisterName.setError("Email is required");
            editTextRegisterName.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            editTextAddress.setError("Addres is required");
            editTextAddress.requestFocus();
            return;
        }
        if (country.isEmpty()) {
            editTextCountry.setError("Country is required");
            editTextCountry.requestFocus();
            return;
        }
        if (description.isEmpty()) {
            editTextDescription.setError("Description is required");
            editTextDescription.requestFocus();
            return;
        }
        if (maxPersons.isEmpty()) {
            editTextMaxPersons.setError("MaxPerson is required");
            editTextMaxPersons.requestFocus();
            return;
        }
        if (minPersons.isEmpty()) {
            editTextMinPersons.setError("MinPerson is required");
            editTextMinPersons.requestFocus();
            return;
        }
        if (latitude.isEmpty()) {
            editTextLatitude.setError("Email is required");
            editTextLatitude.requestFocus();
            return;
        }
        if (longitude.isEmpty()) {
            editTextLongitude.setError("Email is required");
            editTextLongitude.requestFocus();
            return;
        }
        if (cancelingDays.isEmpty()) {
            editTextCancelingDays.setError("CancelingDays is required");
            editTextCancelingDays.requestFocus();
            return;
        }

        if(Integer.parseInt(maxPersons) < Integer.parseInt(minPersons)){
            editTextMaxPersons.setError("Maximum guests lower than the minimum guests");
            editTextMaxPersons.requestFocus();
            return;
        }

        String selectedOptionConfirmation = "";
        if (selectedRadioButtonConfirmationId == -1) {
            return;
        } else {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonConfirmationId);
            selectedOptionConfirmation = selectedRadioButton.getText().toString();
        }

        boolean selectedOptionPricing = false;
        if (selectedRadioButtonPricingId == -1) {
            return;
        } else {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonPricingId);
            if(selectedRadioButton.getText().toString() == "Price by guest"){
                selectedOptionPricing = true;
            }
        }

            // update accommodationDTO with data from layout
            accommodationDTO.setName(name);
            accommodationDTO.setDescription(description);
            accommodationDTO.getLocation().setCountry(country);
            accommodationDTO.getLocation().setAddress(address);
            accommodationDTO.setCapacity(new CapacityDTO());
            accommodationDTO.setConfirmationType(selectedOptionConfirmation);
            accommodationDTO.getCapacity().setMinGuests(Integer.parseInt(minPersons));
            accommodationDTO.getCapacity().setMaxGuests(Integer.parseInt(maxPersons));
            accommodationDTO.setCancelingDays(Long.parseLong(cancelingDays));
            accommodationDTO.setGuestPriced(selectedOptionPricing);
            accommodationDTO.setImage("");
            accommodationDTO.setOwner(owner);

            // update accommodation
            Call<AccommodationDTO> call = ClientUtils.accommodationService.createAccommodation(accommodationDTO);
            call.enqueue(new Callback<AccommodationDTO>() {
                @Override
                public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                    if (response.isSuccessful()) {
                        AccommodationDTO newAccommodationDTO = response.body();
                        uploadImage(newAccommodationDTO.getAccommodationID(), imageUri);
                        setApproveToFalse(newAccommodationDTO.getUpdateAccommodationID());
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAccommodationActivity.this);
                        builder.setTitle("Success");
                        builder.setMessage("Accommodation updated successfully");
                        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }

                @Override
                public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                    Log.e("Error", "Network request failed", t);
                }
            });
        }

    private void setApproveToFalse(Long accommodationID) {
        Call<AccommodationDTO> call = ClientUtils.accommodationService.setApproveAccommodation(accommodationID, false);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                Log.e("Error", "Network request failed", t);
            }
        });
    }

    private void uploadImage(Long accommodationId, Uri imageUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);

            if (inputStream != null) {
                // Create a temporary file to write the image data
                File file = createTempImageFile(inputStream, ".jpg");

                // Get the MIME type of the image using URLConnection
                String mimeType = getMimeTypeFromURLConnection(file);

                // Null check for mimeType
                if (mimeType != null) {
                    // Create RequestBody and MultipartBody.Part for the image file
                    RequestBody requestFile = RequestBody.create(
                            MediaType.parse(mimeType),
                            file
                    );
                    MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

                    // Call the API to upload the image
                    Call<Void> call = ClientUtils.accommodationService.uploadAccommodationPicture(accommodationId, imagePart);
                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            // show success dialog
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterAccommodationActivity.this);
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
                    Log.e("Error", "MIME type is null for selected image");
                }
            } else {
                Log.e("Error", "Failed to open InputStream for selected image");
            }
        } catch (FileNotFoundException e) {
            Log.e("Error", "File not found: " + e.getMessage());
        }
    }

    // Utility method to get MIME type from URLConnection
    private String getMimeTypeFromURLConnection(File file) {
        try {
            URLConnection connection = new URL("file://" + file.getAbsolutePath()).openConnection();
            connection.connect();
            return connection.getContentType();
        } catch (IOException e) {
            Log.e("Error", "Error getting MIME type from URLConnection: " + e.getMessage());
            return null;
        }
    }

    private File createTempImageFile(InputStream inputStream, String fileExtension) {
        try {
            File outputDir = getCacheDir();
            File outputFile = File.createTempFile("temp_image", fileExtension, outputDir);

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

    private void loadUserData() {
        Long username = sharedPreferences.getLong(USER_ID_KEY, 0);

        Call<User> call = ClientUtils.userService.getById(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User userInfoDTO = response.body();
                    owner = userInfoDTO;
                } else {
                    Log.d("Error", "Failed to retrieve user data");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("Error", "Network request failed", t);
            }
        });
    }

    }

