package com.example.bookingappteam17.fragments.accommodations;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
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

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.AccommodationDetailActivity;
import com.example.bookingappteam17.activities.HomeActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.activities.HostAccommodationDetailActivity;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.model.Accommodation;
import com.example.bookingappteam17.services.IAccommodationService;
import com.example.bookingappteam17.viewmodel.SharedViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccommodationListAdapter extends ArrayAdapter<AccommodationCardDTO> {
    private ArrayList<AccommodationCardDTO> aAccommodations;
    private SharedPreferences sharedPreferences;
    private IAccommodationService accommodationService = ClientUtils.accommodationService;
    private SharedViewModel sharedViewModel;


    public AccommodationListAdapter(Context context, ArrayList<AccommodationCardDTO> accommodations, SharedViewModel sharedModel){
        super(context, R.layout.accommodation_card, accommodations);
        aAccommodations = accommodations;
        sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        sharedViewModel = sharedModel;
    }

    public void add(AccommodationCardDTO accommodationCard) {aAccommodations.add(accommodationCard);}

    public void clear() {aAccommodations.clear();    }

    public int getCount() {return aAccommodations.size();}

    public AccommodationCardDTO getItem(int position) {return aAccommodations.get(position);}

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AccommodationCardDTO accommodationCard = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.accommodation_card,
                    parent, false);
        }



        AccommodationDTO accommodation = getAccommodationFromID(accommodationCard.getAccommodationID());

        LinearLayout accommodationCardLayout = convertView.findViewById(R.id.accommodation_card_item);
        TextView accommodationName = convertView.findViewById(R.id.accommodation_name);
        TextView accommodationDescription = convertView.findViewById(R.id.accommodation_description);
        ImageView accommodationImage = convertView.findViewById(R.id.accommodation_image);
        TextView accommodationPrice = convertView.findViewById(R.id.accommodation_price);

        if(accommodationCard != null){
            accommodationName.setText(accommodationCard.getName());
            accommodationDescription.setText(accommodationCard.getDescription());
            accommodationImage.setImageBitmap(accommodationCard.getImage());//            accommodationPrice.setText(String.valueOf(accommodationCard.getPrice()));
            accommodationPrice.setText(String.valueOf(1000));

        }
        Button detailsButton = convertView.findViewById(R.id.accommodation_details);
        detailsButton.setOnClickListener(v -> {
            // get id of accommodation which is clicked
            Long id = accommodationCard.getAccommodationID();
            String role = sharedPreferences.getString("role", "");
            if (role.equals("HOST")) {
                System.out.println("HOST");
                Intent intent = new Intent(getContext(), HostAccommodationDetailActivity.class);
                intent.putExtra("selected_accommodation", id);
                getContext().startActivity(intent);
//                AccommodationPageFragment.accommodations.clear();
            }
            else{
                Intent intent = new Intent(getContext(), AccommodationDetailActivity.class);
                intent.putExtra("selected_accommodation", (Parcelable) accommodation);
                intent.putExtra("sharedViewModel", sharedViewModel);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    public void updateData(List<AccommodationCardDTO> newAccommodations) {
        aAccommodations.clear();
        aAccommodations.addAll(newAccommodations);
        notifyDataSetChanged();
    }

    private AccommodationDTO getAccommodationFromID(Long id){
        final AccommodationDTO[] accommodation = {new AccommodationDTO()};
        Call<AccommodationDTO> call = accommodationService.getAccommodation(id);
        call.enqueue(new Callback<AccommodationDTO>() {
                 @Override
                 public void onResponse(Call<AccommodationDTO> call, Response<AccommodationDTO> response) {
                     if (response.isSuccessful()) {
                         accommodation[0] = response.body();

                     }
                 }

                 @Override
                 public void onFailure(Call<AccommodationDTO> call, Throwable t) {
                     Log.d("Error", t.getMessage());

                 }
             }

        );
        return accommodation[0];
    }
}
