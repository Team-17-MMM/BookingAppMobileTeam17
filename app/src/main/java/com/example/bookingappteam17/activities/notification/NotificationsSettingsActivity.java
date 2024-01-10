package com.example.bookingappteam17.activities.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.notification.EnabledNotificationsDTO;
import com.example.bookingappteam17.dto.notification.NotificationDTO;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationsSettingsActivity extends AppCompatActivity {
    private static final String USER_PREFS_KEY = "user_prefs";

    private SharedPreferences sharedPreferences;

    private EnabledNotificationsDTO enabledNotificationsDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_settings);

        sharedPreferences = getSharedPreferences(USER_PREFS_KEY, Context.MODE_PRIVATE);

        Button buttonLogin = findViewById(R.id.updateNotificationsBtn);
        buttonLogin.setOnClickListener(v -> handleUpdate());

        setOldSettings();

    }

    private void handleUpdate() {
        RadioGroup radioGroupCancel = findViewById(R.id.RadioGroupCancel);
        RadioGroup radioGroupUser = findViewById(R.id.RadioGroupUser);
        RadioGroup radioGroupAccommodation = findViewById(R.id.RadioGroupAccommodation);
        RadioGroup radioGroupRequest = findViewById(R.id.RadioGroupRequest);
        RadioGroup radioGroupRespond = findViewById(R.id.RadioGroupRespond);

        int selectedRadioButtonCancelId = radioGroupCancel.getCheckedRadioButtonId();
        int selectedRadioButtonUserId = radioGroupUser.getCheckedRadioButtonId();
        int selectedRadioButtonAccommodationId = radioGroupAccommodation.getCheckedRadioButtonId();
        int selectedRadioButtonRequestId = radioGroupRequest.getCheckedRadioButtonId();
        int selectedRadioButtonRespondId = radioGroupRespond.getCheckedRadioButtonId();

        boolean selectedOptionCancel;
        String selectedOption = "";
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonCancelId);
        selectedOption = selectedRadioButton.getText().toString();
        if(selectedOption.equals("No")){
            selectedOptionCancel = false;
        }
        else{
            selectedOptionCancel = true;
        }

        boolean selectedOptionUser;
        RadioButton selectedRadioButton1 = findViewById(selectedRadioButtonUserId);
        selectedOption = selectedRadioButton1.getText().toString();
        if(selectedOption.equals("No")){
            selectedOptionUser = false;
        }
        else{
            selectedOptionUser = true;
        }

        boolean selectedOptionAccommodation;
        RadioButton selectedRadioButton2 = findViewById(selectedRadioButtonAccommodationId);
        selectedOption = selectedRadioButton2.getText().toString();
        if(selectedOption.equals("No")){
            selectedOptionAccommodation = false;
        }
        else{
            selectedOptionAccommodation = true;
        }

        boolean selectedOptionRequest;
        RadioButton selectedRadioButton3 = findViewById(selectedRadioButtonRequestId);
        selectedOption = selectedRadioButton3.getText().toString();
        if(selectedOption.equals("No")){
            selectedOptionRequest = false;
        }
        else{
            selectedOptionRequest = true;
        }


        boolean selectedOptionRespond;
        RadioButton selectedRadioButton4 = findViewById(selectedRadioButtonRespondId);
        selectedOption = selectedRadioButton4.getText().toString();
        if(selectedOption.equals("No")){
            selectedOptionRespond = false;
        }
        else{
            selectedOptionRespond = true;
        }

        enabledNotificationsDTO.setCancelReservation(selectedOptionCancel);
        enabledNotificationsDTO.setCreateReservationRequest(selectedOptionRequest);
        enabledNotificationsDTO.setRateUser(selectedOptionUser);
        enabledNotificationsDTO.setRateAccommodation(selectedOptionAccommodation);
        enabledNotificationsDTO.setReservationRequestRespond(selectedOptionRespond);

        Call<EnabledNotificationsDTO> call = ClientUtils.accommodationService.updateEnabledNotifications(enabledNotificationsDTO, enabledNotificationsDTO.getId());
        call.enqueue(new Callback<EnabledNotificationsDTO>() {
            @Override
            public void onResponse(Call<EnabledNotificationsDTO> call, Response<EnabledNotificationsDTO> response) {
                if (response.isSuccessful()) {
                    Log.e("ERROR", "proslo");
                }
            }

            @Override
            public void onFailure(Call<EnabledNotificationsDTO> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });


    }

    private void setOldSettings(){
        Long id = sharedPreferences.getLong("userId", 0);
        Call<EnabledNotificationsDTO> call = ClientUtils.accommodationService.getUserEnabledNotifications(id);
        call.enqueue(new Callback<EnabledNotificationsDTO>() {
            @Override
            public void onResponse(Call<EnabledNotificationsDTO> call, Response<EnabledNotificationsDTO> response) {
                if (response.isSuccessful()) {
                    EnabledNotificationsDTO notifications = response.body();
                    enabledNotificationsDTO = notifications;
                    clickButtons(notifications);
                }
            }

            @Override
            public void onFailure(Call<EnabledNotificationsDTO> call, Throwable t) {
                // Handle failure
                Log.e("ERROR", t.getMessage());
            }
        });
    }

    private void clickButtons(EnabledNotificationsDTO notifications){
        if(notifications.isCancelReservation()){
            int radioButtonToClickId = R.id.radioButtonCancelYes;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }
        else{
            int radioButtonToClickId = R.id.radioButtonCancelYes;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }


        if(notifications.isRateAccommodation()){
            int radioButtonToClickId = R.id.radioButtonAccommodationYes;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }
        else{
            int radioButtonToClickId = R.id.radioButtonAccommodationNo;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }


        if(notifications.isRateUser()){
            int radioButtonToClickId = R.id.radioButtonUserYes;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }
        else{
            int radioButtonToClickId = R.id.radioButtonUserNo;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }


        if(notifications.isCreateReservationRequest()){
            int radioButtonToClickId = R.id.radioButtonRequestYes;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }
        else{
            int radioButtonToClickId = R.id.radioButtonRequestNo;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }


        if(notifications.isReservationRequestRespond()){
            int radioButtonToClickId = R.id.radioButtonRespondYes;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }
        else{
            int radioButtonToClickId = R.id.radioButtonRespondNo;
            RadioButton radioButtonToClick = findViewById(radioButtonToClickId);
            radioButtonToClick.performClick();
        }

    }
}
