package com.example.bookingappteam17.activities.accommodation;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.AvailabilityPeriodRangeDTO;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetPeriodActivity  extends AppCompatActivity {

    private TextInputEditText editTextStartDate;
    private TextInputEditText editTextEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_periods);

        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);

        MaterialDatePicker<Long> materialDatePickerStartDate = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Start Date")
                .build();

        // Set a listener for when the user selects a start date
        materialDatePickerStartDate.addOnPositiveButtonClickListener(selection -> {
            // Format the selected date and set it to the TextInputEditText
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            editTextStartDate.setText(sdf.format(new Date(selection)));
        });

        // Set a click listener for the start date TextInputEditText to show the date picker
        editTextStartDate.setOnClickListener(v ->
                materialDatePickerStartDate.show(getSupportFragmentManager(), "START_DATE_PICKER_TAG"));

        // Set up MaterialDatePicker for end date
        MaterialDatePicker<Long> materialDatePickerEndDate = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select End Date")
                .build();

        // Set a listener for when the user selects an end date
        materialDatePickerEndDate.addOnPositiveButtonClickListener(selection -> {
            // Format the selected date and set it to the TextInputEditText
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            editTextEndDate.setText(sdf.format(new Date(selection)));
        });

        // Set a click listener for the end date TextInputEditText to show the date picker
        editTextEndDate.setOnClickListener(v ->
                materialDatePickerEndDate.show(getSupportFragmentManager(), "END_DATE_PICKER_TAG"));

        Button buttonLogin = findViewById(R.id.setButton);
        buttonLogin.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration(){
        LocalDate startDate;
        LocalDate endDate;
        LocalDate currentDate = LocalDate.now();
        if(editTextStartDate.getText().toString() != "" && editTextEndDate.getText().toString()!= ""){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            startDate = LocalDate.parse(editTextStartDate.getText().toString(), formatter);
            endDate = LocalDate.parse(editTextEndDate.getText().toString(), formatter);

            if (endDate.isBefore(startDate) || startDate.isBefore(currentDate)){
                return;
            }

            Long selectedAccommodation = getIntent().getLongExtra("selected_accommodation", 0);

            EditText editTextPrice = findViewById(R.id.accommodationPriceEditText);

            String price = editTextPrice.getText().toString();
            if (price.isEmpty()) {
                editTextPrice.setError("Price is required");
                editTextPrice.requestFocus();
                return;
            }
            AvailabilityPeriodRangeDTO availabilityPeriodRangeDTO = new AvailabilityPeriodRangeDTO(startDate.toString(), endDate.toString(), Long.parseLong(price));

            Call<AccommodationDTO> call = ClientUtils.accommodationService.updateAccommodationPeriod(availabilityPeriodRangeDTO, selectedAccommodation);

            call.enqueue(new Callback<AccommodationDTO>() {
                @Override
                public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                    if (response.isSuccessful()) {
                        AccommodationDTO newAccommodationDTO = response.body();
                        AlertDialog.Builder builder = new AlertDialog.Builder(SetPeriodActivity.this);
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


    }
}
