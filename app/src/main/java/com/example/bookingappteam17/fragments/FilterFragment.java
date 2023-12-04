package com.example.bookingappteam17.fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.enums.ResortType;
import com.example.bookingappteam17.listener.CircleTouchListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class FilterFragment extends BottomSheetDialogFragment {

    private Button buttonApplyFilter;
    private TextInputLayout textInputLayoutStartDate;
    private TextInputEditText editTextStartDate;
    private TextInputLayout textInputLayoutEndDate;
    private TextInputEditText editTextEndDate;
    private View connectionLine;
    private ImageView minThumb;
    private ImageView maxThumb;
    private TextView minPriceTextView;
    private TextView maxPriceTextView;
    private float minValue = 0;
    private float maxValue = 100;
    private RadioGroup radioGroupResortType;
    private ResortType chosenResortType;
    private EditText editTextCity;
    private Spinner spinnerPeopleCount;
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
        connectionLine = view.findViewById(R.id.connectionLine);
        minThumb = view.findViewById(R.id.minThumb);
        maxThumb = view.findViewById(R.id.maxThumb);
        minPriceTextView = view.findViewById(R.id.minPriceTextView);
        maxPriceTextView = view.findViewById(R.id.maxPriceTextView);
        radioGroupResortType = view.findViewById(R.id.radioGroupResortType);
        spinnerPeopleCount = view.findViewById(R.id.spinnerPeopleCount);
        editTextCity = view.findViewById(R.id.editTextCity);
        chosenResortType = ResortType.APARTMENT;

        updateTextViews();
        minThumb.setOnTouchListener(new CircleTouchListener(this,true));
        maxThumb.setOnTouchListener(new CircleTouchListener(this,false));


        // Create a list of options (e.g., 1 to 10 people)
        List<String> peopleOptions = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            peopleOptions.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, peopleOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeopleCount.setAdapter(adapter);
        int defaultSelection = 2;
        spinnerPeopleCount.setSelection(adapter.getPosition(String.valueOf(defaultSelection)));

        // Set a listener for the RadioGroup
        radioGroupResortType.setOnCheckedChangeListener((group, checkedId) -> {
            // Check which radio button was selected
            if (checkedId == R.id.radioButtonApartment) {
                chosenResortType = ResortType.APARTMENT;
            } else if (checkedId == R.id.radioButtonStudio) {
                chosenResortType = ResortType.STUDIO;
            }
        });

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

        String enteredCity = editTextCity.getText().toString();
        int selectedPeopleCount = Integer.parseInt(spinnerPeopleCount.getSelectedItem().toString());

        // For demonstration purposes, show a Toast with the filter values
        String message = "Start Date: " + startDate + "\nEnd Date: " + endDate;
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();

        // Dismiss the BottomSheetDialogFragment after applying the filter
        dismiss();
    }
    public void updateTextViews() {
        minPriceTextView.setText("Min: $" + (int) minValue);
        maxPriceTextView.setText("Max: $" + (int) maxValue);
    }

    public float calculateValueFromPositionMin(float x) {
        float percent = x / connectionLine.getWidth();
        float calculatedValue = getMinValue() + (percent * 100);
        return Math.max(0, Math.min(getMaxValue(), calculatedValue)); // Ensure the value is within the range [0, 100]
    }

    public float calculateValueFromPositionMax(float x) {
        float percent = x / connectionLine.getWidth();
        float calculatedValue = getMaxValue() + (percent * 100);
        return Math.max(getMinValue(), Math.min(100, calculatedValue)); // Ensure the value is within the range [getMinValue(), 100]
    }


    public float getMinValue(){
        return minValue;
    }
    public float getMaxValue(){
        return maxValue;
    }
    public void setMinValue(float value) {
        minValue = value;
    }

    public void setMaxValue(float value) {
        maxValue = value;
    }
    public void updateMinThumbPosition(float x) {
        if(minThumb.getX() + x < maxThumb.getX()){
            if(minThumb.getX() + x < 0){
                minThumb.setX(0);
                return;
            }
            minThumb.setX(minThumb.getX() + x);
        }
    }

    public void updateMaxThumbPosition(float x) {
        if(maxThumb.getX() + x > minThumb.getX()){
            if(maxThumb.getX() + x > connectionLine.getWidth() + maxThumb.getWidth()){
                maxThumb.setX(connectionLine.getWidth() + maxThumb.getWidth());
                return;
            }
            maxThumb.setX(maxThumb.getX() + x);
        }
    }

}
