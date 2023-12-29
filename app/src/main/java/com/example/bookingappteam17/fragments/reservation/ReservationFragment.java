package com.example.bookingappteam17.fragments.reservation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.CapacityDTO;
import com.example.bookingappteam17.dto.reservation.ReservationPostDTO;
import com.example.bookingappteam17.enums.reservation.ReservationStatus;
import com.example.bookingappteam17.model.reservation.Reservation;
import com.example.bookingappteam17.services.reservation.IReservationService;
import com.example.bookingappteam17.viewmodel.SharedViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFragment extends DialogFragment {
    private Button buttonCreateReservation;
    private TextInputEditText editTextStartDate;
    private TextInputEditText editTextEndDate;
    private Spinner spinnerPeopleCount;
    private AccommodationDTO accommodation;
    private IReservationService reservationService = ClientUtils.reservationService;
    private SharedViewModel sharedViewModel;

    public ReservationFragment(AccommodationDTO accommodationDTO, SharedViewModel sharedModel){
        accommodation=accommodationDTO;
        sharedViewModel = sharedModel;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reservation, container, false);
        setStyle(STYLE_NORMAL, R.style.Theme_BookingAppTeam17);

        buttonCreateReservation = view.findViewById(R.id.btnCreateReservation);
        editTextStartDate = view.findViewById(R.id.editTextStartDate);
        editTextEndDate = view.findViewById(R.id.editTextEndDate);
        spinnerPeopleCount = view.findViewById(R.id.spinnerPeopleCount);

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

        List<String> peopleOptions = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            peopleOptions.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, getOccupancy());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeopleCount.setAdapter(adapter);
        int defaultSelection = 2;
        spinnerPeopleCount.setSelection(adapter.getPosition(String.valueOf(defaultSelection)));

        buttonCreateReservation.setOnClickListener(v -> createReservation(view));


        return view;
    }

    private void createReservation(View view){
        LocalDate startDate;
        LocalDate endDate;

        if(editTextStartDate.getText().toString() != "" && editTextEndDate.getText().toString()!= ""){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            startDate = LocalDate.parse(editTextStartDate.getText().toString(), formatter);
            endDate = LocalDate.parse(editTextEndDate.getText().toString(), formatter);
        }else{
            showShortToast(this.getContext(),"Date range is required");
            return;
        }

        Long occupancy = Long.parseLong(spinnerPeopleCount.getSelectedItem().toString());

        ReservationPostDTO reservationDTO = new ReservationPostDTO(sharedViewModel.getUserInfoDTO().getUserID(),accommodation.getAccommodationID(),startDate,endDate, ReservationStatus.PENDING,3000);

        Call<Reservation> call = reservationService.createReservation(reservationDTO);
        call.enqueue(new Callback<Reservation>() {
                 @Override
                 public void onResponse(Call<Reservation> call, Response<Reservation> response) {
                     if (response.isSuccessful()) {
                         Reservation reservation = response.body();
                         showLongToast(view.getContext(),"Successfully created reservation!");
                     }
                 }

                 @Override
                 public void onFailure(Call<Reservation> call, Throwable t) {
                     Log.d("Error", t.getMessage());
                     showLongToast(view.getContext(),"Error creating reservation!");
                 }
             }
        );

        dismiss();

    }

    private List<String> getOccupancy(){
        List<String> occupancy  = new ArrayList<>();
        CapacityDTO capacityDTO = accommodation.getCapacity();
        for(int i = capacityDTO.getMinGuests(); i<capacityDTO.getMaxGuests(); ++i){
            occupancy.add(String.valueOf(i));
        }
        return occupancy;
    }

    private static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
