package com.example.bookingappteam17.fragments.notification;

import android.content.Context;
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
import com.example.bookingappteam17.model.notification.Notification;

import java.util.ArrayList;

public class NotificationListAdapter extends ArrayAdapter<Notification> {
    private ArrayList<Notification> aNotifications;
    public NotificationListAdapter(Context context, ArrayList<Notification> notifications) {
        super(context, R.layout.notification_card, notifications);
        aNotifications = notifications;
    }

    public int getCount() {
        return aNotifications.size();
    }

    public Notification getItem(int position) {
        return aNotifications.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public void add(Notification notification) {
        aNotifications.add(notification);
    }

    public void remove(Notification notification) {
        aNotifications.remove(notification);
    }

    public void clear() {
        aNotifications.clear();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Notification notification = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.notification_card,
                    parent, false);
        }
        LinearLayout productCard = convertView.findViewById(R.id.notification_card_item);
        TextView productTitle = convertView.findViewById(R.id.notification_title);
        TextView productDescription = convertView.findViewById(R.id.notification_description);

        if(notification != null){
            productTitle.setText(notification.getTitle());
            productDescription.setText(notification.getDescription());

            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + notification.getTitle() + ", id: " +
                        notification.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + notification.getTitle()  +
                        ", id: " + notification.getId().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        return convertView;
    }

}
