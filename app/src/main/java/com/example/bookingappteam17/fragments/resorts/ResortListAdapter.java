package com.example.bookingappteam17.fragments.resorts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.model.Resort;

import java.util.ArrayList;

public class ResortListAdapter extends ArrayAdapter<Resort> {
    private ArrayList<Resort> aResorts;

    public ResortListAdapter(Context context, ArrayList<Resort> resorts){
        super(context, R.layout.resort_card, resorts);
        aResorts = resorts;

    }

    public void add(Resort resort) {aResorts.add(resort);}

    public void clear() {aResorts.clear();    }

    public int getCount() {return aResorts.size();}

    public Resort getItem(int position) {return aResorts.get(position);}

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Resort resort = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.resort_card,
                    parent, false);
        }
        LinearLayout resortCard = convertView.findViewById(R.id.resort_card_item);
        TextView resortName = convertView.findViewById(R.id.resort_name);
        TextView resortDescription = convertView.findViewById(R.id.resort_description);
        ImageView resortImage = convertView.findViewById(R.id.resort_image);
        TextView resortPrice = convertView.findViewById(R.id.resort_price);

        if(resort != null){
            resortName.setText(resort.getName());
            resortDescription.setText(resort.getDescription());
            resortImage.setImageResource(resort.getImage());
            resortPrice.setText(String.valueOf(resort.getPrice()));
            resortCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + resort.getName() + ", id: " +
                        resort.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + resort.getName()  +
                        ", id: " + resort.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }
}
