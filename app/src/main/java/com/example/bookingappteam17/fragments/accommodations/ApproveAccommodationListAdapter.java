package com.example.bookingappteam17.fragments.accommodations;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationUpdateDTO;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveAccommodationListAdapter extends ArrayAdapter<AccommodationCardDTO> {

    private List<AccommodationCardDTO> aAccommodations;
    private Context context;

    public ApproveAccommodationListAdapter(Context context, List<AccommodationCardDTO> accommodations) {
        super(context, R.layout.approve_accommodation_card, accommodations);
        this.context = context;
        this.aAccommodations = accommodations;
    }

    public void setAccommodations(List<AccommodationCardDTO> accommodations) {
        aAccommodations.clear();
        aAccommodations.addAll(accommodations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationCardDTO accommodation = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.approve_accommodation_card, parent, false);
        }

        LinearLayout accommodationCard = convertView.findViewById(R.id.approve_accommodation_card_item);
        TextView accommodationName = convertView.findViewById(R.id.approve_accommodation_name);
        TextView accommodationDescription = convertView.findViewById(R.id.approve_accommodation_description);
        ImageView accommodationImage = convertView.findViewById(R.id.approve_accommodation_image);
        TextView accommodationPrice = convertView.findViewById(R.id.approve_accommodation_price);

        if (accommodation != null) {
            accommodationName.setText(accommodation.getName());
            accommodationDescription.setText(accommodation.getDescription());
            accommodationImage.setImageBitmap(accommodation.getImage());
            accommodationPrice.setText(String.valueOf(1000)); // You might want to update this based on the actual data
        }

        Button approveButton = convertView.findViewById(R.id.button_approve);
        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccommodation(accommodation.getAccommodationID());
                Snackbar.make(v, "Accommodation approved", Snackbar.LENGTH_LONG).show();
            }
        });

        Button rejectButton = convertView.findViewById(R.id.button_reject);
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteNewAccommodation(accommodation.getAccommodationID());
                Snackbar.make(v, "Accommodation rejected", Snackbar.LENGTH_LONG).show();
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
                // Remove the accommodation from the local list
                aAccommodations.removeIf(a -> a.getAccommodationID().equals(accommodationID));
                // Notify the adapter that the data set has changed
                notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<AccommodationDTO> call, Throwable t) {
            // Handle failure
            Log.d("ApproveAccommodationListAdapter", "onFailure: " + t.getMessage());
        }
    });
}

private void deleteNewAccommodation(Long accommodationID) {
    Call<AccommodationDTO> call = ClientUtils.accommodationService.deleteAccommodation(accommodationID);
    call.enqueue(new Callback<AccommodationDTO>() {
        @Override
        public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
            if (response.isSuccessful()) {
                // Remove the accommodation from the local list
                aAccommodations.removeIf(a -> a.getAccommodationID().equals(accommodationID));
                // Notify the adapter that the data set has changed
                notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Call<AccommodationDTO> call, Throwable t) {
            // Handle failure
            Log.d("ApproveAccommodationListAdapter", "onFailure: " + t.getMessage());
        }
    });
}

    private void notifyDataChanged() {
        if (context instanceof LifecycleOwner) {
            ((LifecycleOwner) context).getLifecycle().getCurrentState();
        }
    }
}
