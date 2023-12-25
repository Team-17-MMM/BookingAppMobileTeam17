package com.example.bookingappteam17.fragments.accommodations;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.AccommodationDetailActivity;
import com.example.bookingappteam17.activities.HostAccommodationDetailActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationUpdateDTO;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveAccommodationListAdapter extends ArrayAdapter<AccommodationCardDTO> {
    private ArrayList<AccommodationCardDTO> aAccommodations;
    private SharedPreferences sharedPreferences;

    public ApproveAccommodationListAdapter(Context context, ArrayList<AccommodationCardDTO> accommodations){
        super(context, R.layout.approve_accommodation_card, accommodations);
        aAccommodations = accommodations;
        sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    }

    public void add(AccommodationCardDTO accommodation) {aAccommodations.add(accommodation);}

    public void clear() {aAccommodations.clear();    }

    public int getCount() {return aAccommodations.size();}

    public AccommodationCardDTO getItem(int position) {return aAccommodations.get(position);}

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationCardDTO accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.approve_accommodation_card,
                    parent, false);
        }
        LinearLayout accommodationCard = convertView.findViewById(R.id.approve_accommodation_card_item);
        TextView accommodationName = convertView.findViewById(R.id.approve_accommodation_name);
        TextView accommodationDescription = convertView.findViewById(R.id.approve_accommodation_description);
        ImageView accommodationImage = convertView.findViewById(R.id.approve_accommodation_image);
        TextView accommodationPrice = convertView.findViewById(R.id.approve_accommodation_price);

        if(accommodation != null){
            accommodationName.setText(accommodation.getName());
            accommodationDescription.setText(accommodation.getDescription());
            accommodationImage.setImageBitmap(accommodation.getImage());
//            accommodationPrice.setText(String.valueOf(accommodation.getPrice()));
            accommodationPrice.setText(String.valueOf(1000));

        }

        Button approveButton = convertView.findViewById(R.id.button_approve);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // update accommodation
                updateAccommodation(accommodation.getAccommodationID());
                // delete selected
//                deleteNewAccommodation(accommodation.getAccommodationID());
                // refresh list and load it again
                ApproveAccommodationPageFragment.accommodations.clear();
                ApproveAccommodationPageFragment.newInstance();
            }

        });


        Button rejectButton = convertView.findViewById(R.id.button_reject);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete selected
                deleteNewAccommodation(accommodation.getAccommodationID());
                // refresh list and load it again
                ApproveAccommodationPageFragment.accommodations.clear();
                ApproveAccommodationPageFragment.newInstance();
            }
        });

        return convertView;
    }


    private void updateAccommodation(Long accommodationID) {
        Call<AccommodationDTO> call = ClientUtils.accommodationService.updateByNewAccommodation(accommodationID);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    System.out.println("Success");
                    Toast.makeText(getContext(), "Accommodation approved!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }

    private void deleteNewAccommodation(Long accommodationID) {
        Call<AccommodationDTO> call = ClientUtils.accommodationService.deleteAccommodation(accommodationID);
        call.enqueue(new Callback<AccommodationDTO>() {
            @Override
            public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                if (response.isSuccessful()) {
                    System.out.println("Success");
                    Toast.makeText(getContext(), "Accommodation rejected!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                System.out.println("Error");
            }
        });
    }
}
