package com.example.bookingappteam17.fragments.accommodations;

import android.content.Context;
import android.content.Intent;
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

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.AccommodationDetailActivity;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.model.Accommodation;

import java.util.ArrayList;

public class AccommodationListAdapter extends ArrayAdapter<AccommodationCardDTO> {
    private ArrayList<AccommodationCardDTO> aAccommodations;

    public AccommodationListAdapter(Context context, ArrayList<AccommodationCardDTO> accommodations){
        super(context, R.layout.accommodation_card, accommodations);
        aAccommodations = accommodations;

    }

    public void add(AccommodationCardDTO accommodation) {aAccommodations.add(accommodation);}

    public void clear() {aAccommodations.clear();    }

    public int getCount() {return aAccommodations.size();}

    public AccommodationCardDTO getItem(int position) {return aAccommodations.get(position);}

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationCardDTO accommodation = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_card,
                    parent, false);
        }
        LinearLayout accommodationCard = convertView.findViewById(R.id.accommodation_card_item);
        TextView accommodationName = convertView.findViewById(R.id.accommodation_name);
        TextView accommodationDescription = convertView.findViewById(R.id.accommodation_description);
        ImageView accommodationImage = convertView.findViewById(R.id.accommodation_image);
        TextView accommodationPrice = convertView.findViewById(R.id.accommodation_price);

        if(accommodation != null){
            accommodationName.setText(accommodation.getName());
            accommodationDescription.setText(accommodation.getDescription());
//            accommodationImage.setImageResource(accommodation.getImage());
//            accommodationPrice.setText(String.valueOf(accommodation.getPrice()));
            accommodationPrice.setText(String.valueOf(1000));

        }
        Button detaljiButton = convertView.findViewById(R.id.accommodation_detalji);
        detaljiButton.setOnClickListener(v -> {
            // Handle click on "Detalji" button
            Intent intent = new Intent(getContext(), AccommodationDetailActivity.class);
            intent.putExtra("selected_accommodation", (CharSequence) accommodation);
            getContext().startActivity(intent);
        });

        return convertView;
    }
}
