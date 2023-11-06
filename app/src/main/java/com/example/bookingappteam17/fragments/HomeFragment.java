package com.example.bookingappteam17.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.databinding.ActivityHomeBinding;
import com.example.bookingappteam17.databinding.FragmentResortsPageBinding;
import com.example.bookingappteam17.fragments.resorts.ResortListFragment;
import com.example.bookingappteam17.fragments.resorts.ResortPageFragment;
import com.example.bookingappteam17.fragments.resorts.ResortPageViewModel;
import com.example.bookingappteam17.model.Resort;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_home, container, false);

        // Instantiate PageFragment and add it to the layout
        FragmentTransition.to(ResortPageFragment.newInstance(), getActivity(), false, R.id.home_frame_layout);
        return view;
    }
}
