package com.example.bookingappteam17.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.bookingappteam17.databinding.FragmentNotificationsListBinding;
import com.example.bookingappteam17.model.Notification;

import java.util.ArrayList;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("BookingApp", "onCreateView Notifications List Fragment");
        binding = FragmentNotificationsListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("BookingApp", "onCreate Notifications List Fragment");
        if (getArguments() != null) {
            mNotifications = getArguments().getParcelableArrayList(ARG_PARAM);
            adapter = new NotificationListAdapter(getActivity(), mNotifications);
            setListAdapter(adapter);
        }
    }
}
