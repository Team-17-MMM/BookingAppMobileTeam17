package com.example.bookingappteam17.fragments.notification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.databinding.FragmentNotificationsListBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.notification.NotificationDTO;
import com.example.bookingappteam17.fragments.accommodation.AccommodationListAdapter;
import com.example.bookingappteam17.fragments.accommodation.FavoriteAccommodationPageViewModel;
import com.example.bookingappteam17.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationsListFragment extends ListFragment {
    private FragmentNotificationsListBinding binding;
    private static final String ARG_PARAM = "param";
    private ArrayList<Notification> mNotifications;
    private NotificationListAdapter adapter;

    public static NotificationsListFragment newInstance(ArrayList<Notification> notifications) {
        NotificationsListFragment fragment = new NotificationsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM, notifications);
        fragment.setArguments(args);
        return fragment;
    }

    public NotificationListAdapter getAdapter(){
        return adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("BookingApp", "onCreateView Notifications List Fragment");
        binding = FragmentNotificationsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NotificationsPageViewModel viewModel = new ViewModelProvider(this).get(NotificationsPageViewModel.class);

        // Set up the adapter
        adapter = new NotificationListAdapter(getActivity(), new ArrayList<>());
        setListAdapter(adapter);


        // Observe LiveData and update UI
        viewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<NotificationDTO>>() {
            @Override
            public void onChanged(List<NotificationDTO> accommodationCardDTOS) {
                adapter.clear();
                adapter.addAll(accommodationCardDTOS);
                adapter.notifyDataSetChanged();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
        viewModel.loadNotifications(sharedPreferences.getString("username", ""),sharedPreferences.getLong("userId", 1));


        return root;
    }

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("BookingApp", "onCreate Notifications List Fragment");
        if (getArguments() != null) {
            mNotifications = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new NotificationListAdapter(getActivity(), mNotifications);
            setListAdapter(adapter);
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
