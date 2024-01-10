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
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.notification.NotificationDTO;
import com.example.bookingappteam17.enums.notification.NotificationType;
import com.example.bookingappteam17.model.notification.Notification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        TextView productDescription = convertView.findViewById(R.id.notification_description);

        if(notification != null){


            if(notification.getNotificationType() == NotificationType.RATE_USER){
                productDescription.setText("You have received a new review from a guest.");
            }
            else if(notification.getNotificationType() == NotificationType.RATE_ACCOMMODATION){
                productDescription.setText("A guest has reviewed your accommodation.");
            }
            else if(notification.getNotificationType() == NotificationType.RESERVATION_REQUEST_RESPOND){
                productDescription.setText("Good news! The host has responded to your reservation request.");
            }
            else if(notification.getNotificationType() == NotificationType.CANCEL_RESERVATION){
                productDescription.setText("Your reservation has been canceled.");
            }
            else if(notification.getNotificationType() == NotificationType.CREATE_RESERVATION_REQUEST){
                productDescription.setText("Good news! You have received a new reservation request.");
            }

            productCard.setOnClickListener(v -> {
                // Handle click on the item at 'position'
                Log.i("ShopApp", "Clicked: " + notification.getNotificationType().toString() + ", id: " +
                        notification.getId().toString());
                Toast.makeText(getContext(), "Clicked: " + notification.getNotificationType().toString()  +
                        ", id: " + notification.getId().toString(), Toast.LENGTH_SHORT).show();
                notification.setShown(true);
                Call<NotificationDTO> call = ClientUtils.accommodationService.updateNotification(notification, notification.getId());
                call.enqueue(new Callback<NotificationDTO>() {
                    @Override
                    public void onResponse(Call<NotificationDTO> call, Response<NotificationDTO> response) {
                        if (response.isSuccessful()) {
                            NotificationDTO notifications = response.body();
                            Log.i("ok", "proslo");
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationDTO> call, Throwable t) {
                        // Handle failure
                        Log.e("ERROR", t.getMessage());
                    }
                });
            });
        }

        return convertView;
    }

}
