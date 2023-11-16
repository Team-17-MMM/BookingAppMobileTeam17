package com.example.bookingappteam17.fragments.resorts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.bookingappteam17.databinding.FragmentResortListBinding;
import com.example.bookingappteam17.model.Resort;
import java.util.ArrayList;

public class ResortListFragment extends ListFragment {
    private ResortListAdapter adapter;
    private static final String ARG_PARAM = "param";
    private ArrayList<Resort> mResorts;
    private FragmentResortListBinding binding;

    public ResortListAdapter getAdapter() {
        return adapter;
    }

    public static ResortListFragment newInstance(ArrayList<Resort> resorts){
        ResortListFragment fragment = new ResortListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, resorts);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentResortListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResorts = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new ResortListAdapter(getActivity(), mResorts);
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
