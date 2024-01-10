package com.example.bookingappteam17.fragments.notification;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.databinding.FragmentNotificationsPageBinding;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.notification.NotificationDTO;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.fragments.accommodation.AccommodationListAdapter;
import com.example.bookingappteam17.fragments.accommodation.FavoriteAccommodationListFragment;
import com.example.bookingappteam17.fragments.accommodation.FavoriteAccommodationPageViewModel;
import com.example.bookingappteam17.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationPageFragment extends Fragment {
    private NotificationsPageViewModel notificationsViewModel;
    private FragmentNotificationsPageBinding binding;

    private NotificationsListFragment notificationsListFragment;

    private SharedPreferences sharedPreferences;
    ArrayList<NotificationDTO> notifications = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences("user_prefs", getActivity().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        Long id = sharedPreferences.getLong("userId", 1);
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsPageViewModel.class);

        binding = FragmentNotificationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize the list fragment
        notificationsListFragment = new NotificationsListFragment();

        // Set up the adapter
        NotificationListAdapter adapter = new NotificationListAdapter(getActivity(), notifications);
        notificationsListFragment.setListAdapter(adapter);

        // Load data
        notificationsViewModel.loadNotifications(username, id);


        notificationsViewModel.getNotificationsLiveData().observe(getViewLifecycleOwner(), new Observer<List<NotificationDTO>>() {
            @Override
            public void onChanged(List<NotificationDTO> accommodationCardDTOS) {
                adapter.setNotifications(accommodationCardDTOS);
            }
        });


//        Calls FragmentTransition.to to replace the layout with a NotificationsListFragment.
        FragmentTransition.to(notificationsListFragment, getActivity(), false, R.id.scroll_notifications_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
