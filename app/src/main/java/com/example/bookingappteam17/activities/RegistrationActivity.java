package com.example.bookingappteam17.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.UserInfoDTO;
import com.example.bookingappteam17.dto.UserRegistrationDTO;
import com.example.bookingappteam17.enums.UserRoleType;
import com.example.bookingappteam17.services.IUserService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    private IUserService userService = ClientUtils.userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(v -> handleRegistration());
    }

    private void handleRegistration() {
        EditText editTextRegisterEmailAddress = findViewById(R.id.editTextRegisterEmailAddress);
        EditText editTextRegisterName = findViewById(R.id.editTextRegisterName);
        EditText editTextRegisterSurName = findViewById(R.id.editTextRegisterSurname);
        EditText editTextRegisterPassword = findViewById(R.id.editTextRegisterPassword);
        EditText editTextRegisterConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        EditText editTextRegisterPhone = findViewById(R.id.editTextRegisterPhone);
        EditText editTextRegisterAddress = findViewById(R.id.editTextRegisterAddress);
        EditText editTextRegisterCountry = findViewById(R.id.editTextRegisterCountry);


        String email = editTextRegisterEmailAddress.getText().toString();
        String password = editTextRegisterPassword.getText().toString();
        String name = editTextRegisterName.getText().toString();
        String surname = editTextRegisterSurName.getText().toString();
        String confirmPassword = editTextRegisterConfirmPassword.getText().toString();
        String phone = editTextRegisterPhone.getText().toString();
        String address = editTextRegisterAddress.getText().toString();
        String country = editTextRegisterCountry.getText().toString();

        if (email.isEmpty()) {
            editTextRegisterEmailAddress.setError("Email is required");
            editTextRegisterEmailAddress.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextRegisterPassword.setError("Password is required");
            editTextRegisterPassword.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            editTextRegisterName.setError("Email is required");
            editTextRegisterName.requestFocus();
            return;
        }
        if (surname.isEmpty()) {
            editTextRegisterSurName.setError("Email is required");
            editTextRegisterSurName.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            editTextRegisterConfirmPassword.setError("Email is required");
            editTextRegisterConfirmPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            editTextRegisterPhone.setError("Email is required");
            editTextRegisterPhone.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            editTextRegisterAddress.setError("Email is required");
            editTextRegisterAddress.requestFocus();
            return;
        }
        if (country.isEmpty()) {
            editTextRegisterCountry.setError("Email is required");
            editTextRegisterCountry.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword)){
            editTextRegisterConfirmPassword.setError("Passwords do NOT match");
            editTextRegisterConfirmPassword.requestFocus();
            return;
        }

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername(email);
        userRegistrationDTO.setPassword(password);
        userRegistrationDTO.setAddress(address + " " + country);
        userRegistrationDTO.setName(name);
        userRegistrationDTO.setLastname(surname);
        userRegistrationDTO.setPhone(phone);
        userRegistrationDTO.setUserRole(UserRoleType.HOST);

        Call<UserRegistrationDTO> call = userService.registerAccount(userRegistrationDTO);
        call.enqueue(new Callback<UserRegistrationDTO>() {
            @Override
            public void onResponse(Call<UserRegistrationDTO> call, Response<UserRegistrationDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserRegistrationDTO entity = response.body();
                }
                else {
                    Log.d("Error", "Failed to retrieve user data");
                }
            }
            @Override
            public void onFailure(Call<UserRegistrationDTO> call, Throwable t) {
                Log.e("Error", "Network request failed", t);
            }
    });}
}