package com.example.bookingappteam17.fragments.accommodation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.bookingappteam17.activities.home.HomeActivity;
import com.example.bookingappteam17.databinding.FragmentAccommodationListBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.viewmodel.SharedViewModel;

import java.util.ArrayList;

public class AccommodationListFragment extends ListFragment {
    private AccommodationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<AccommodationCardDTO> mAccommodations;
    private FragmentAccommodationListBinding binding;
    private SharedViewModel sharedViewModel;


    public AccommodationListAdapter getAdapter() {
        return adapter;
    }

    public static AccommodationListFragment newInstance(ArrayList<AccommodationCardDTO> accommodations){
        AccommodationListFragment fragment = new AccommodationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, accommodations);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateAccommodations(ArrayList<AccommodationCardDTO> accommodations) {
        this.mAccommodations = accommodations;
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccommodationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            if (getActivity() != null && getActivity() instanceof HomeActivity) {
                sharedViewModel = ((HomeActivity) getActivity()).getSharedViewModel();
            }
            adapter = new AccommodationListAdapter(getActivity(), mAccommodations, sharedViewModel);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // Handle the click on item at 'position'
    }

}
