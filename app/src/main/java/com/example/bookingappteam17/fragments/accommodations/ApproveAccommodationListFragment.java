package com.example.bookingappteam17.fragments.accommodations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.bookingappteam17.databinding.FragmentAccommodationListBinding;
import com.example.bookingappteam17.databinding.FragmentApproveAccommodationListBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;

import java.util.ArrayList;

public class ApproveAccommodationListFragment extends ListFragment {
    private ApproveAccommodationListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<AccommodationCardDTO> mAccommodations;
    private FragmentApproveAccommodationListBinding binding;

    public ApproveAccommodationListAdapter getAdapter() {
        return adapter;
    }

    public static ApproveAccommodationListFragment newInstance(ArrayList<AccommodationCardDTO> accommodations){
        ApproveAccommodationListFragment fragment = new ApproveAccommodationListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, accommodations);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentApproveAccommodationListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAccommodations = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ApproveAccommodationListAdapter(getActivity(), mAccommodations);
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