package com.example.bookingappteam17.activities.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.user.UserRegistrationDTO;
import com.example.bookingappteam17.enums.user.UserRoleType;
import com.example.bookingappteam17.services.user.IUserService;


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
        RadioGroup radioGroup = findViewById(R.id.RadioGroupUserRole);
        TextView radioGroupError = findViewById(R.id.radioGroupError);

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();


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
            editTextRegisterName.setError("Name is required");
            editTextRegisterName.requestFocus();
            return;
        }
        if (surname.isEmpty()) {
            editTextRegisterSurName.setError("Surname is required");
            editTextRegisterSurName.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            editTextRegisterConfirmPassword.setError("ConfirmPassword is required");
            editTextRegisterConfirmPassword.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            editTextRegisterPhone.setError("Phone is required");
            editTextRegisterPhone.requestFocus();
            return;
        }
        if (address.isEmpty()) {
            editTextRegisterAddress.setError("Address is required");
            editTextRegisterAddress.requestFocus();
            return;
        }
        if (country.isEmpty()) {
            editTextRegisterCountry.setError("Country is required");
            editTextRegisterCountry.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword)){
            editTextRegisterConfirmPassword.setError("Passwords do NOT match");
            editTextRegisterConfirmPassword.requestFocus();
            return;
        }
        String selectedOption = "";
        if (selectedRadioButtonId == -1) {
            radioGroupError.setVisibility(View.VISIBLE);
            radioGroupError.setText("Role is required");
            return;
        } else {
            radioGroupError.setVisibility(View.GONE);
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            selectedOption = selectedRadioButton.getText().toString();
        }

        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUsername(email);
        userRegistrationDTO.setPassword(password);
        userRegistrationDTO.setAddress(address + " " + country);
        userRegistrationDTO.setName(name);
        userRegistrationDTO.setLastname(surname);
        userRegistrationDTO.setPhone(phone);
        userRegistrationDTO.setUserRole(UserRoleType.valueOf(selectedOption));

        Call<UserRegistrationDTO> call = userService.registerAccount(userRegistrationDTO);
        call.enqueue(new Callback<UserRegistrationDTO>() {
            @Override
            public void onResponse(Call<UserRegistrationDTO> call, Response<UserRegistrationDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserRegistrationDTO entity = response.body();
                    Toast.makeText(RegistrationActivity.this, "Confirmation mail sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
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