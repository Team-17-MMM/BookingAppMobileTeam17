package com.example.bookingappteam17.fragments.reservation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.home.HomeActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.AvailabilityPeriodDTO;
import com.example.bookingappteam17.dto.accommodation.CapacityDTO;
import com.example.bookingappteam17.dto.reservation.ReservationDTO;
import com.example.bookingappteam17.enums.reservation.ReservationStatus;
import com.example.bookingappteam17.services.reservation.IReservationService;
import com.example.bookingappteam17.validators.ReservationDateValidator;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationFragment extends BottomSheetDialogFragment {
    private Button buttonCreateReservation;
    private TextInputEditText editTextStartDate;
    private TextInputEditText editTextEndDate;
    private TextInputEditText priceText;
    private Spinner spinnerPeopleCount;
    private AccommodationDTO accommodation;
    private IReservationService reservationService = ClientUtils.reservationService;
    private Long userID;

    public ReservationFragment(AccommodationDTO accommodationDTO, Long userID){
        accommodation=accommodationDTO;
        this.userID = userID;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_reservation, container, false);
        setStyle(STYLE_NORMAL, R.style.Theme_BookingAppTeam17);

        getDialog().setOnDismissListener(this);

        buttonCreateReservation = view.findViewById(R.id.btnCreateReservation);
        editTextStartDate = view.findViewById(R.id.editTextStartDate);
        editTextEndDate = view.findViewById(R.id.editTextEndDate);
        spinnerPeopleCount = view.findViewById(R.id.spinnerPeopleCount);
        priceText = view.findViewById(R.id.priceEditText);

        CalendarConstraints.Builder startDateConstraintsBuilder = new CalendarConstraints.Builder();

        startDateConstraintsBuilder.setValidator(new ReservationDateValidator(getStartDates()));



        MaterialDatePicker<Long> materialDatePickerStartDate = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select Start Date")
                .setCalendarConstraints(startDateConstraintsBuilder.build())
                .build();

        editTextEndDate.setEnabled(false);

        // Set a listener for when the user selects a start date
        materialDatePickerStartDate.addOnPositiveButtonClickListener(selection -> {
            // Format the selected date and set it to the TextInputEditText
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
            editTextStartDate.setText(sdf.format(new Date(selection)));

            if(!editTextEndDate.getText().toString().equals("")){
                try {
                    Date startDatedate = sdf.parse(editTextStartDate.getText().toString());
                    Date endDatedate = sdf.parse(editTextEndDate.getText().toString());
                    if(endDatedate.before(startDatedate) || endDatedate.equals(startDatedate)){
                        editTextEndDate.setText("");
                        priceText.setText("");
                    }else{
                        priceText.setText(String.valueOf(setPrice(startDatedate,endDatedate,Long.parseLong(spinnerPeopleCount.getSelectedItem().toString()))));
                    }
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
                CalendarConstraints.Builder endDateConstraintsBuilder = new CalendarConstraints.Builder();
            try {
                endDateConstraintsBuilder.setValidator(new ReservationDateValidator(getEndDates(sdf.parse(editTextStartDate.getText().toString()))));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            MaterialDatePicker<Long> materialDatePickerEndDate = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select End Date")
                        .setCalendarConstraints(endDateConstraintsBuilder.build())
                        .build();

                materialDatePickerEndDate.addOnPositiveButtonClickListener(time -> {
                    // Format the selected date and set it to the TextInputEditText
                    editTextEndDate.setText(sdf.format(new Date(time)));
                    try {
                        Date startDatedate = sdf.parse(editTextStartDate.getText().toString());
                        Date endDatedate = sdf.parse(editTextEndDate.getText().toString());
                        priceText.setText(String.valueOf(setPrice(startDatedate,endDatedate,Long.parseLong(spinnerPeopleCount.getSelectedItem().toString()))));

                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                });

                // Set a click listener for the end date TextInputEditText to show the date picker
                editTextEndDate.setOnClickListener(v ->
                        materialDatePickerEndDate.show(requireActivity().getSupportFragmentManager(), "END_DATE_PICKER_TAG"));

                editTextEndDate.setEnabled(true);


        });

        // Set a click listener for the start date TextInputEditText to show the date picker
        editTextStartDate.setOnClickListener(v ->
                materialDatePickerStartDate.show(requireActivity().getSupportFragmentManager(), "START_DATE_PICKER_TAG"));



        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, getOccupancy());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeopleCount.setAdapter(adapter);
        spinnerPeopleCount.setSelection(adapter.getPosition(String.valueOf(accommodation.getCapacity().getMinGuests())));

        spinnerPeopleCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
                if(!editTextEndDate.getText().toString().equals("") && !editTextStartDate.getText().toString().equals("")){
                    try {
                        priceText.setText(String.valueOf(setPrice(sdf.parse(editTextStartDate.getText().toString()), sdf.parse(editTextEndDate.getText().toString()),Long.parseLong(spinnerPeopleCount.getSelectedItem().toString()))));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        buttonCreateReservation.setOnClickListener(v -> createReservation(view));


        return view;
    }

    private int setPrice(Date date1, Date date2, Long occupancy){
        LocalDate startDate = DateToLocalDate(date1);
        LocalDate endDate = DateToLocalDate(date2);
        int price = 0;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateStr = startDate.format(dateFormatter);
        List<LocalDate> endLocalDates = new ArrayList<>();

        boolean isAfterStartDate = false;
        for(AvailabilityPeriodDTO availabilityPeriodDTO: sortAvailabilityPeriods(accommodation.getAvailabilityPeriods())){
            LocalDate tempDate = LocalDate.parse(availabilityPeriodDTO.getDate(), dateFormatter);
            if(isAfterStartDate){
                if(tempDate.isEqual(endLocalDates.get(endLocalDates.size() - 1).plusDays(1)) && tempDate.isBefore(endDate)){
                    endLocalDates.add(tempDate);
                    price += availabilityPeriodDTO.getPrice();
                    continue;
                }
                break;
            } else if(availabilityPeriodDTO.getDate().equals(startDateStr)){
                isAfterStartDate = true;
                price += availabilityPeriodDTO.getPrice();
                endLocalDates.add(tempDate);
            }

        }

        if(accommodation.getGuestPriced()){
            price *= occupancy;
        }
        return price;

    }

    private void createReservation(View view){
        LocalDate startDate;
        LocalDate endDate;

        if(editTextStartDate.getText().toString() != "" && editTextEndDate.getText().toString()!= ""){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            startDate = LocalDate.parse(editTextStartDate.getText().toString(), formatter);
            endDate = LocalDate.parse(editTextEndDate.getText().toString(), formatter);
        }else{
            showShortToast(this.getContext(),"Date range is required");
            return;
        }

        Long occupancy = Long.parseLong(spinnerPeopleCount.getSelectedItem().toString());

        if(occupancy < accommodation.getCapacity().getMinGuests() || occupancy > accommodation.getCapacity().getMaxGuests()){
            showShortToast(this.getContext(),"Occupancy not whitin range\nMin: " + accommodation.getCapacity().getMinGuests() + "\tMax: " + accommodation.getCapacity().getMaxGuests());
            return;
        }

        ReservationDTO reservationDTO = new ReservationDTO(userID,accommodation.getAccommodationID(),startDate.toString(),endDate.toString(), ReservationStatus.PENDING,Integer.parseInt(String.valueOf(priceText.getText())));

        Call<ReservationDTO> call = reservationService.createReservation(reservationDTO);
        call.enqueue(new Callback<ReservationDTO>() {
                 @Override
                 public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                     if (response.isSuccessful()) {
                         ReservationDTO reservation = response.body();
                         showLongToast(view.getContext(),"Successfully created reservation!");
                         if(accommodation.getConfirmationType().equals("Auto Confirmation")){
                             approveReservation(view,reservation);
                         }
                         dismiss();
                     }
                 }

                 @Override
                 public void onFailure(Call<ReservationDTO> call, Throwable t) {
                     Log.d("Error", t.getMessage());
                     showLongToast(view.getContext(),"Error creating reservation!");
                     dismiss();
                 }
             }
        );



    }

    private void approveReservation(View view, ReservationDTO reservationDTO){
        Call<HashSet<ReservationDTO>> call = reservationService.acceptReservation(reservationDTO.getReservationID(), reservationDTO.getReservationID());
        call.enqueue(new Callback<HashSet<ReservationDTO>>() {
                 @Override
                 public void onResponse(Call<HashSet<ReservationDTO>> call, Response<HashSet<ReservationDTO>> response) {
                     if (response.isSuccessful()) {
                         showShortToast(view.getContext(),"Reservation accepted automatically");
                     }
                 }

                 @Override
                 public void onFailure(Call<HashSet<ReservationDTO>> call, Throwable t) {
                     Log.d("Error", t.getMessage());
                     showLongToast(view.getContext(),"Error creating reservation!");
                     dismiss();
                 }
             }
        );
    }

    private List<String> getOccupancy(){
        List<String> occupancy  = new ArrayList<>();
        CapacityDTO capacityDTO = accommodation.getCapacity();
        for(int i = capacityDTO.getMinGuests(); i<capacityDTO.getMaxGuests(); ++i){
            occupancy.add(String.valueOf(i));
        }
        occupancy.add(String.valueOf(Integer.parseInt(occupancy.get(occupancy.size()-1)) + 1));
        return occupancy;
    }

    private static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    private long localDateToTimestamp(LocalDate localDate) {
        // Convert LocalDate to LocalDateTime by adding a default time (midnight)
        LocalDateTime localDateTime = localDate.atStartOfDay();

        // Convert LocalDateTime to ZonedDateTime by specifying a time zone
        Date date = Date.from(localDateTime.atZone(ZoneOffset.UTC).toInstant());


        // Return the timestamp
        return date.getTime();
    }

    public static LocalDate DateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private Set<AvailabilityPeriodDTO> sortAvailabilityPeriods(Set<AvailabilityPeriodDTO> periods) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Comparator<AvailabilityPeriodDTO> byStartDate = Comparator.comparing(
                period -> LocalDate.parse(period.getDate(), dateFormatter)
        );

        // Use a TreeSet to automatically sort the elements
        Set<AvailabilityPeriodDTO> sortedPeriods = new TreeSet<>(byStartDate);
        sortedPeriods.addAll(periods);

        return sortedPeriods;
    }

    private List<Long> getStartDates(){
        HashSet<Long> startDates = new HashSet<>();
        for (AvailabilityPeriodDTO availabilityPeriodDTO: accommodation.getAvailabilityPeriods()){
            String dateStr = availabilityPeriodDTO.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate startDate = LocalDate.parse(dateStr, formatter);
            startDates.add(localDateToTimestamp(startDate));
        }
        return new ArrayList<>(startDates);
    }

    private List<Long> getEndDates(Date date){

        LocalDate startDate = DateToLocalDate(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = startDate.format(formatter);
        HashSet<Long> endDates = new HashSet<>();
        List<LocalDate> endLocalDates = new ArrayList<>();

        boolean isAfterStartDate = false;
        Set<AvailabilityPeriodDTO> sortedAvailabilityPeriods = sortAvailabilityPeriods(accommodation.getAvailabilityPeriods());

        for (AvailabilityPeriodDTO availabilityPeriodDTO: sortedAvailabilityPeriods){
            LocalDate tempDate = LocalDate.parse(availabilityPeriodDTO.getDate(), formatter);
            if(isAfterStartDate){
                if(tempDate.isEqual(endLocalDates.get(endLocalDates.size() - 1).plusDays(1))){
                    endLocalDates.add(tempDate);
                    continue;
                }
                break;
            } else if(availabilityPeriodDTO.getDate().equals(formattedDate)){
                isAfterStartDate = true;
                endLocalDates.add(tempDate);
            }
        }
        if (endLocalDates.isEmpty()){
            endLocalDates.add(startDate.plusDays(1));
        }else{
            endLocalDates.add(endLocalDates.get(endLocalDates.size() - 1).plusDays(1));
            endLocalDates.remove(0);
        }

        for(LocalDate date1: endLocalDates){
            endDates.add(localDateToTimestamp(date1));
        }
        return new ArrayList<>(endDates);
    }


}
