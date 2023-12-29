package com.example.bookingappteam17.fragments.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.databinding.FragmentNotificationsPageBinding;
import com.example.bookingappteam17.fragments.FragmentTransition;
import com.example.bookingappteam17.model.notification.Notification;

import java.util.ArrayList;

public class NotificationPageFragment extends Fragment {
    private NotificationsPageViewModel notificationsViewModel;
    private FragmentNotificationsPageBinding binding;
    ArrayList<Notification> notifications = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel = new ViewModelProvider(this).get(NotificationsPageViewModel.class);

        binding = FragmentNotificationsPageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        prepareNotificationList(notifications);

//        Calls FragmentTransition.to to replace the layout with a NotificationsListFragment.
        FragmentTransition.to(NotificationsListFragment.newInstance(notifications), getActivity(), false, R.id.scroll_notifications_list);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void prepareNotificationList(ArrayList<Notification> notifications) {
        // add random examples
        notifications.add(new Notification(1L, "Notification 1", "This is the first notification"));
        notifications.add(new Notification(2L, "Notification 2", "This is the second notification"));
        notifications.add(new Notification(3L, "Notification 3", "This is the third notification"));
        notifications.add(new Notification(4L, "Notification 4", "This is the fourth notification"));
        notifications.add(new Notification(5L, "Notification 5", "This is the fifth notification"));
        notifications.add(new Notification(6L, "Notification 6", "This is the sixth notification"));
    }
}
