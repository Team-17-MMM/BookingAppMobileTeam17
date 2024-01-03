package com.example.bookingappteam17.fragments;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.HomeActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.listener.CircleTouchListener;
import com.example.bookingappteam17.services.IAccommodationService;
import com.example.bookingappteam17.services.IAmenitiesService;
import com.example.bookingappteam17.viewmodel.SharedViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.time.format.DateTimeFormatter;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterFragment extends BottomSheetDialogFragment {

    public interface OnDismissListener {
        void onDismiss();
    }
    private OnDismissListener dismissListener;
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
    private float maxValue = 100000;
    private List<String> chosenAccommodationTypes;
    private EditText editTextCity;
    private Spinner spinnerPeopleCount;
    private List<String> allAmenities = new ArrayList<>();
    private SharedViewModel sharedViewModel;
    private IAccommodationService accommodationService = ClientUtils.accommodationService;
    private List<AccommodationCardDTO> filteredAccommodations = new ArrayList<>();

    private IAmenitiesService amenitiesService = ClientUtils.amenitiesService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        setStyle(STYLE_NORMAL, R.style.Theme_BookingAppTeam17);

        if (getActivity() != null && getActivity() instanceof HomeActivity) {
            sharedViewModel = ((HomeActivity) getActivity()).getSharedViewModel();
        }

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
        spinnerPeopleCount = view.findViewById(R.id.spinnerPeopleCount);
        editTextCity = view.findViewById(R.id.editTextCity);

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


        // Set up MaterialDatePicker for start date
        MaterialDatePicker<Long> materialDatePickerStartDate = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Start Date")
                .build();

        // Set a listener for when the user selects a start date
        materialDatePickerStartDate.addOnPositiveButtonClickListener(selection -> {
            // Format the selected date and set it to the TextInputEditText
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
            editTextEndDate.setText(sdf.format(new Date(selection)));
        });

        // Set a click listener for the end date TextInputEditText to show the date picker
        editTextEndDate.setOnClickListener(v ->
                materialDatePickerEndDate.show(getParentFragmentManager(), "END_DATE_PICKER_TAG"));


        allAmenities = getAllAmenities(view);


        // Set a click listener for the Apply Filter button
        buttonApplyFilter.setOnClickListener(v -> applyFilter(view));

        return view;
    }

    private void applyFilter(View view) {
        LocalDate startDate;
        LocalDate endDate;
        String city = editTextCity.getText().toString();
        String startDateString = editTextStartDate.getText().toString();
        String endDateString = editTextEndDate.getText().toString();

        System.out.println(editTextStartDate.getText().toString());
        if(city.equals("")){
            showLongToast(this.getContext(),"City name is required");
            return;
        }
        if(!startDateString.equals("") && !endDateString.equals("")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            startDate = LocalDate.parse(editTextStartDate.getText().toString(), formatter);
            endDate = LocalDate.parse(editTextEndDate.getText().toString(), formatter);
        }else{
            showLongToast(this.getContext(),"Date range is required");
            return;
        }
        chosenAccommodationTypes = getSelectedAccommodationTypes(view);

        String enteredCity = editTextCity.getText().toString();
        Long occupancy = Long.parseLong(spinnerPeopleCount.getSelectedItem().toString());

        Long minPrice = (long) minValue;
        Long maxPrice = (long) maxValue;
        List<String> checkedAmenities = getCheckedAmenities(view);
        String[] accommodationArray = chosenAccommodationTypes.toArray(new String[0]);
        String[] amenitiesArray = checkedAmenities.toArray(new String[0]);

        Call<HashSet<AccommodationCardDTO>> call = accommodationService.searchAccommodations(enteredCity,editTextStartDate.getText().toString(),editTextEndDate.getText().toString(),occupancy,minPrice,maxPrice,amenitiesArray,accommodationArray);
        call.enqueue(new Callback<HashSet<AccommodationCardDTO>>() {
             @Override
             public void onResponse(Call<HashSet<AccommodationCardDTO>> call, Response<HashSet<AccommodationCardDTO>> response) {
                 if (response.isSuccessful()) {
                     HashSet<AccommodationCardDTO> accommodations = response.body();
                     for (AccommodationCardDTO accommodationCardDTO : accommodations) {
                         filteredAccommodations.add(accommodationCardDTO);
                     }
                     sharedViewModel.setAccommodationCards(filteredAccommodations);
                     dismiss();
                 }
             }

             @Override
             public void onFailure(Call<HashSet<AccommodationCardDTO>> call, Throwable t) {
                 Log.d("Error", t.getMessage());
             }
         }
        );

    }

    public void setOnDismissListener(OnDismissListener listener) {
        this.dismissListener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }

    public void updateTextViews() {
        minPriceTextView.setText("Min: " + (int) minValue + " RSD");
        maxPriceTextView.setText("Max: " + (int) maxValue + " RSD");
    }

    public float calculateValueFromPositionMin(float x) {
        float percent = x / connectionLine.getWidth();
        float calculatedValue = getMinValue() + (percent * 100000);
        return Math.max(0, Math.min(getMaxValue(), calculatedValue));
    }

    public float calculateValueFromPositionMax(float x) {
        float percent = x / connectionLine.getWidth();
        float calculatedValue = getMaxValue() + (percent * 100000);
        return Math.max(getMinValue(), Math.min(100000, calculatedValue));
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

    private List<String> getSelectedAccommodationTypes(View view){
        CheckBox checkboxRoom = view.findViewById(R.id.checkboxRoom);
        CheckBox checkboxStudio = view.findViewById(R.id.checkboxStudio);
        CheckBox checkboxApartment = view.findViewById(R.id.checkboxApartment);
        List<String> selectedTypes = new ArrayList<>();

        if (checkboxRoom.isChecked()) {
            selectedTypes.add(checkboxRoom.getText().toString());
        }

        if (checkboxStudio.isChecked()) {
            selectedTypes.add(checkboxStudio.getText().toString());
        }

        if (checkboxApartment.isChecked()) {
            selectedTypes.add(checkboxApartment.getText().toString());
        }

        return selectedTypes;
    }

    private List<String> getAllAmenities(View view){
        List<String> allAmenities = new ArrayList<>();

        Call<List<String>> call = amenitiesService.getAllAmenities();
        call.enqueue(new Callback<List<String>>() {
             @Override
             public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                 if (response.isSuccessful()) {
                     GridLayout checkboxContainer = view.findViewById(R.id.checkboxContainer);
                     List<String> amenities = response.body();
                     allAmenities.addAll(amenities);

                     for (String amenity : allAmenities) {
                         CheckBox checkBox = new CheckBox(view.getContext());
                         checkBox.setText(amenity);

                         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                             checkBox.setButtonTintList(ContextCompat.getColorStateList(view.getContext(), R.color.blue));
                         }

                         GridLayout.Spec rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Row weight
                         GridLayout.Spec colSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Column weight

                         GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);
                         params.width = 0;
                         params.height = GridLayout.LayoutParams.WRAP_CONTENT;


                         checkBox.setLayoutParams(params);

                         checkboxContainer.addView(checkBox);
                     }
                 }
             }

             @Override
             public void onFailure(Call<List<String>> call, Throwable t) {
                 Log.d("Error", t.getMessage());

             }
         }
        );

        return allAmenities;
    }

    private List<String> getCheckedAmenities(View view) {
        List<String> checkedAmenities = new ArrayList<>();

        GridLayout checkboxContainer = view.findViewById(R.id.checkboxContainer);

        // Iterate through the dynamically created CheckBox instances
        for (int i = 0; i < checkboxContainer.getChildCount(); i++) {
            View childView = checkboxContainer.getChildAt(i);

            // Check if the child view is a CheckBox
            if (childView instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) childView;

                // Check if the CheckBox is checked
                if (checkBox.isChecked()) {
                    checkedAmenities.add(allAmenities.get(i));
                }
            }
        }

        return checkedAmenities;
    }


    private static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
