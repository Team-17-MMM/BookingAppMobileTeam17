package com.example.bookingappteam17.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingappteam17.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterFragment extends BottomSheetDialogFragment {

    private Button buttonApplyFilter;
    private TextInputLayout textInputLayoutStartDate;
    private TextInputEditText editTextStartDate;
    private TextInputLayout textInputLayoutEndDate;
    private TextInputEditText editTextEndDate;

    private MaterialDatePicker<Long> materialDatePicker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        setStyle(STYLE_NORMAL, R.style.Theme_BookingAppTeam17);

        buttonApplyFilter = view.findViewById(R.id.btnApplyFilter);
        textInputLayoutStartDate = view.findViewById(R.id.textInputLayoutStartDate);
        editTextStartDate = view.findViewById(R.id.editTextStartDate);
        textInputLayoutEndDate = view.findViewById(R.id.textInputLayoutEndDate);
        editTextEndDate = view.findViewById(R.id.editTextEndDate);

        // Set up MaterialDatePicker for start date
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
                materialDatePickerStartDate.show(getParentFragmentManager(), "START_DATE_PICKER_TAG"));

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
                materialDatePickerEndDate.show(getParentFragmentManager(), "END_DATE_PICKER_TAG"));

        // Set a click listener for the Apply Filter button
        buttonApplyFilter.setOnClickListener(v -> applyFilter());

        return view;
    }

    private void applyFilter() {
        String startDate = editTextStartDate.getText().toString();
        String endDate = editTextEndDate.getText().toString();

        // Handle your filter logic using the start and end dates
        // ...

        // For demonstration purposes, show a Toast with the filter values
        String message = "Start Date: " + startDate + "\nEnd Date: " + endDate;
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

        // Dismiss the BottomSheetDialogFragment after applying the filter
        dismiss();
    }

}
