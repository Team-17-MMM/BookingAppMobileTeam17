package com.example.bookingappteam17.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.model.Resort;

import java.util.ArrayList;

public class ResortListAdapter extends ArrayAdapter<Resort> {
    private ArrayList<Resort> aResorts;

    public ResortListAdapter(Context context, ArrayList<Resort> resorts){
        super(context, R.layout.resort_card, resorts);
        aResorts = resorts;

    }
}
