package com.example.bookingappteam17.fragments.reservation;

import static com.example.bookingappteam17.clients.ClientUtils.reservationService;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;
import com.example.bookingappteam17.dto.reservation.ReservationFilterRequestDTO;
import com.example.bookingappteam17.dto.reservation.ReservationInfoDTO;
import com.example.bookingappteam17.fragments.accommodation.AccommodationFilterFragment;
import com.example.bookingappteam17.listener.CircleTouchListener;
import com.example.bookingappteam17.services.accommodation.IAccommodationService;
import com.example.bookingappteam17.services.accommodation.IAmenitiesService;
import com.example.bookingappteam17.services.reservation.IReservationService;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFilterFragment extends BottomSheetDialogFragment {

    public interface OnDismissListener {
        void onDismiss();
    }
    private ReservationFilterFragment.OnDismissListener dismissListener;
    private Button buttonApplyFilter;
    private Long userID;
    private TextInputEditText editTextStartDate;
    private TextInputEditText editTextEndDate;
    private EditText editAccommodationName;
    private Spinner spinner;
    private IReservationService reservationService = ClientUtils.reservationService;

    private List<ReservationInfoDTO> filteredReservations = new ArrayList<>();

    private ReservationsListFragment fragment;

    public ReservationFilterFragment(ReservationsListFragment fragment, Long userID){
        this.fragment = fragment;
        this.userID = userID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservation_filter, container, false);
        setStyle(STYLE_NORMAL, R.style.Theme_BookingAppTeam17);


        buttonApplyFilter = view.findViewById(R.id.btnApplyFilter);
        editTextStartDate = view.findViewById(R.id.editTextStartDate);
        editTextEndDate = view.findViewById(R.id.editTextEndDate);
        editAccommodationName = view.findViewById(R.id.editAccommodationName);

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
                materialDatePickerStartDate.show(getParentFragmentManager(), "RESERVATION_START_DATE_PICKER_TAG"));

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
                materialDatePickerEndDate.show(getParentFragmentManager(), "RESERVATION_END_DATE_PICKER_TAG"));


        // Set a click listener for the Apply Filter button
        buttonApplyFilter.setOnClickListener(v -> applyFilter(view));

        spinner = view.findViewById(R.id.btnSort);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.status_array));
        // Specify the layout to use when the list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapter);

        return view;
    }

    private void applyFilter(View view) {
        String accommodationName = editAccommodationName.getText().toString();
        String startDateString = editTextStartDate.getText().toString();
        String endDateString = editTextEndDate.getText().toString();
        String selectedItem = spinner.getSelectedItem().toString();

        Call<HashSet<ReservationInfoDTO>> call = reservationService.filterReservationsFromList(new ReservationFilterRequestDTO(userID,editTextStartDate.getText().toString(),editTextEndDate.getText().toString(),accommodationName,selectedItem));
        call.enqueue(new Callback<HashSet<ReservationInfoDTO>>() {
                 @Override
                 public void onResponse(Call<HashSet<ReservationInfoDTO>> call, Response<HashSet<ReservationInfoDTO>> response) {
                     if (response.isSuccessful()) {
                         HashSet<ReservationInfoDTO> reservations = response.body();
                         fragment.updateReservationList(new ArrayList<>(reservations));
                         dismiss();
                     }
                 }

                 @Override
                 public void onFailure(Call<HashSet<ReservationInfoDTO>> call, Throwable t) {
                     Log.d("Error", t.getMessage());
                 }
             }
        );

    }

    public void setOnDismissListener(ReservationFilterFragment.OnDismissListener listener) {
        this.dismissListener = listener;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (dismissListener != null) {
            dismissListener.onDismiss();
        }
    }


    private static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
