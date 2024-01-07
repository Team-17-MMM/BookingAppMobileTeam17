package com.example.bookingappteam17.fragments.reservation;
import static com.example.bookingappteam17.clients.ClientUtils.reservationService;
import static com.example.bookingappteam17.clients.ClientUtils.accommodationService;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.reservation.ReservationDTO;
import com.example.bookingappteam17.dto.reservation.ReservationInfoDTO;
import com.example.bookingappteam17.dto.reservation.ReservationReportDTO;
import com.example.bookingappteam17.enums.reservation.ReservationStatus;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservationListAdapter extends ArrayAdapter<ReservationInfoDTO> {
    private List<ReservationInfoDTO> reservations;
    private ReservationsListFragment fragment;
    private Context context;
    private SharedPreferences sharedPreferences;

    Button cancelButton;

    Button approveButton;

    public ReservationListAdapter(Context context, List<ReservationInfoDTO> reservations,  ReservationsListFragment fragment) {
        super(context, R.layout.reservation_card);
        this.context = context;
        this.reservations = reservations;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ReservationInfoDTO reservation = getItem(position);
        sharedPreferences = context.getSharedPreferences("user_prefs", context.MODE_PRIVATE);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reservation_card, parent, false);
        }

        TextInputEditText priceField =convertView.findViewById(R.id.price);
        TextInputEditText startDateField = convertView.findViewById(R.id.startDate);
        TextInputEditText endDateField = convertView.findViewById(R.id.endDate);
        TextInputEditText statusField = convertView.findViewById(R.id.status);
        TextInputEditText accommodationNameField = convertView.findViewById(R.id.accommodationName);
        TextInputEditText userNameField = convertView.findViewById(R.id.name);

        if(reservation!=null){
            priceField.setText(String.valueOf(reservation.getPrice()));
            startDateField.setText(reservation.getStartDate());
            endDateField.setText(reservation.getEndDate());
            statusField.setText(reservation.getStatus().name());
            userNameField.setText(reservation.getUserFirstName() + " " + reservation.getUserLastName());
            accommodationNameField.setText(reservation.getAccommodationName());
        }

        reservations.add(reservation);

        approveButton = convertView.findViewById(R.id.approveBtn);
        if(reservation.getStatus().equals(ReservationStatus.PENDING) && sharedPreferences.getString("role","").equals("HOST")){
            approveButton.setEnabled(true);
        }else{
            approveButton.setEnabled(false);
        }

        approveButton.setOnClickListener(v -> {
            Call<HashSet<ReservationDTO>> call = reservationService.acceptReservation(reservation.getReservationID(), reservation.getReservationID());
            call.enqueue(new Callback<HashSet<ReservationDTO>>() {
            @Override
            public void onResponse(Call<HashSet<ReservationDTO>> call, Response<HashSet<ReservationDTO>> response) {
                if (response.isSuccessful()) {
                    fragment.reloadReservations();
                    showShortToast(getContext(),"Successfully accepted reservation");
                }
            }

            @Override
            public void onFailure(Call<HashSet<ReservationDTO>> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
        });

        cancelButton = convertView.findViewById(R.id.cancelBtn);

        if(reservation.getStatus().equals(ReservationStatus.CANCELED) || reservation.getStatus().equals(ReservationStatus.COMPLETED) || reservation.getStatus().equals(ReservationStatus.REJECTED)){
            cancelButton.setEnabled(false);
        }else{
            cancelButton.setEnabled(true);
        }

        cancelButton.setOnClickListener(v -> {
            if(sharedPreferences.getString("role","").equals("HOST")){
                Call<ReservationDTO> call = reservationService.rejectReservation(reservation.getReservationID(), reservation.getReservationID());
                call.enqueue(new Callback<ReservationDTO>() {
                    @Override
                    public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                        if (response.isSuccessful()) {
                            fragment.reloadReservations();
                            showShortToast(getContext(),"Successfully rejected reservation");
                        }else{
                            showLongToast(getContext(),"Can't reject this reservation");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationDTO> call, Throwable t) {
                        // Handle failure
                        Log.e("ERROR", t.getMessage());
                    }
                });
            }else if (sharedPreferences.getString("role","").equals("GUEST")){
                Call<ReservationDTO> call = reservationService.cancelReservation(reservation.getReservationID(), reservation.getReservationID());
                call.enqueue(new Callback<ReservationDTO>() {
                    @Override
                    public void onResponse(Call<ReservationDTO> call, Response<ReservationDTO> response) {
                        if (response.isSuccessful()) {

                            fragment.reloadReservations();
                            showShortToast(getContext(),"Successfully canceled reservation");
                        }else{
                            showLongToast(getContext(),"Can't cancel this reservation");
                        }
                    }

                    @Override
                    public void onFailure(Call<ReservationDTO> call, Throwable t) {
                        // Handle failure
                        Log.e("ERROR", t.getMessage());
                    }
                });
            }

        });


        return convertView;
    }

    private static void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
