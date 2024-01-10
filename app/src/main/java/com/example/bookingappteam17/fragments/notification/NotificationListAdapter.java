package com.example.bookingappteam17.fragments.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.notification.NotificationDTO;
import com.example.bookingappteam17.model.notification.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationListAdapter extends ArrayAdapter<NotificationDTO> {
    private ArrayList<NotificationDTO> aNotifications;

    private Context context;
    private SharedPreferences sharedPreferences;

    public NotificationListAdapter(Context context, ArrayList<NotificationDTO> notifications) {
        super(context, R.layout.notification_card, notifications);
        this.context = context;
        aNotifications = notifications;
    }

    public void setNotifications(List<NotificationDTO> accommodations) {
        aNotifications.clear();
        aNotifications.addAll(accommodations);
        notifyDataSetChanged();
    }

    public int getCount() {
        return aNotifications.size();
    }

    public NotificationDTO getItem(int position) {
        return aNotifications.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void add(NotificationDTO notification) {
        aNotifications.add(notification);
    }

    public void remove(NotificationDTO notification) {
        aNotifications.remove(notification);
    }

    public void clear() {
        aNotifications.clear();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NotificationDTO notification = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.notification_card_item);
        TextView productTitle = convertView.findViewById(R.id.notification_title);
        TextView productDescription = convertView.findViewById(R.id.notification_description);

        if(notification != null){
            productTitle.setText(notification.getNotificationType().toString());
            productDescription.setText(notification.getNotificationType().toString());

            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + notification.getNotificationType().toString() + ", id: " +
                        notification.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + notification.getNotificationType().toString()  +
                        ", id: " + notification.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }

}
