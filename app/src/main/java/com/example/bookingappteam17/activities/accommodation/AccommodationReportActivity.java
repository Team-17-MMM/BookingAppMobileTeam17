package com.example.bookingappteam17.activities.accommodation;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.adapters.AccommodationReportAdapter;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.databinding.ActivityAccommodationReportBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationReportDTO;
import com.example.bookingappteam17.dto.reservation.ReservationReportDTO;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationReportActivity extends AppCompatActivity {
    ActivityAccommodationReportBinding binding;
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAccommodationReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String username = getIntent().getStringExtra("username");



        Button getReport = binding.btnGenerateReport;
        getReport.setOnClickListener(v -> {
            EditText startDate = binding.editTextStartDate;
            EditText endDate = binding.editTextEndDate;
            if (Objects.requireNonNull(startDate.getText()).toString().isEmpty()
                    || Objects.requireNonNull(endDate.getText()).toString().isEmpty()) {
                startDate.setError("Please enter start date");
                endDate.setError("Please enter end date");
            } else {
                String start = startDate.getText().toString();
                String end = endDate.getText().toString();
                generateReport(username, start, end);
            }
        });

        EditText editTextStartDate = findViewById(R.id.editTextStartDate);
        EditText editTextEndDate = findViewById(R.id.editTextEndDate);

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
    }

    private void generateReport(String username, String start, String end) {
        // TODO: Load data from the server
        Call<List<AccommodationReportDTO>> call = ClientUtils.reservationService.getHostReport(username, start, end);
        call.enqueue(new Callback<List<AccommodationReportDTO>>() {
            @Override
            public void onResponse(Call<List<AccommodationReportDTO>> call, Response<List<AccommodationReportDTO>> response) {
                if (response.isSuccessful()) {
                    List<AccommodationReportDTO> accommodationReportDTOS = response.body();
                    createReportDialog(accommodationReportDTOS);
                    Log.d("TAG", "onResponse: " + accommodationReportDTOS);
                }
            }

            @Override
            public void onFailure(Call<List<AccommodationReportDTO>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void createReportDialog(List<AccommodationReportDTO> accommodationList) {

        // Create a dialog
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.diaolog_table_report);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
        }

        // Initialize RecyclerView
        RecyclerView recyclerView = dialog.findViewById(R.id.tableRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create an instance of your custom adapter
        AccommodationReportAdapter adapter = new AccommodationReportAdapter(accommodationList);

        // Set adapter to RecyclerView
        recyclerView.setAdapter(adapter);

        // Show the dialog
        Button okButton = dialog.findViewById(R.id.dialog_ok_button);
        okButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        Button getPdfButton = dialog.findViewById(R.id.dialog_get_pdf_button);
        getPdfButton.setOnClickListener(v -> {

        });
        dialog.show();
    }

}
