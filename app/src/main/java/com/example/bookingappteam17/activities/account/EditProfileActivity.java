package com.example.bookingappteam17.activities.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.accommodation.FavoriteAccommodationsActivity;
import com.example.bookingappteam17.activities.accommodation.HostAccommodationDetailActivity;
import com.example.bookingappteam17.activities.accommodation.SetPeriodActivity;
import com.example.bookingappteam17.activities.authentication.LoginActivity;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.dto.user.UserInfoDTO;
import com.example.bookingappteam17.dto.user.UserLoginDTO;
import com.example.bookingappteam17.dto.user.UserUpdateDTO;
import com.example.bookingappteam17.model.user.AuthResponse;
import com.example.bookingappteam17.viewmodel.SharedViewModel;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private UserInfoDTO userInfoDTO;
    private SharedViewModel sharedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        // Retrieve UserInfoDTO from intent
        userInfoDTO = (UserInfoDTO) getIntent().getSerializableExtra("userInfoDTO");

        // Ensure userInfoDTO is not null before accessing its properties
        if (userInfoDTO != null) {
            setupData();
            // set name to textview
            EditText name = findViewById(R.id.editTextName);
            name.setText(userInfoDTO.getName());

            // Access other properties as needed
        } else {
            // Handle the case where userInfoDTO is null
            // For example, display an error message or take appropriate action
            Log.e("EditProfileActivity", "userInfoDTO is null");
        }

        // handle click on save button
        Button saveButton = findViewById(R.id.btnSaveUser);
        saveButton.setOnClickListener(v -> {
            updateUser();
        });

        // handle click on delete button
        Button deleteButton = findViewById(R.id.btnDeleteAccount);
        deleteButton.setOnClickListener(v -> {
            deleteUser();
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        // Add any additional setup code as needed
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Add any additional setup code as needed
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Add any cleanup or save state code as needed
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Add any cleanup or save state code as needed
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Add any cleanup code as needed
    }


    private void setupData() {
        EditText name = findViewById(R.id.editTextName);
        name.setText(userInfoDTO.getName());

        EditText lastname = findViewById(R.id.editTextLastName);
        lastname.setText(userInfoDTO.getLastname());

        EditText email = findViewById(R.id.editTextEmail);
        email.setText(userInfoDTO.getUsername());

        EditText address = findViewById(R.id.editTextAddress);
        address.setText(userInfoDTO.getAddress());

        EditText phone = findViewById(R.id.editTextPhone);
        phone.setText(userInfoDTO.getPhone());
    }

    private void updateUser() {
        // check if editTextOldPassword is empty, if empty save user without changing password
        // else check if editTextOldPassword is corrected and then compare editTextNewPassword and editTextConfirmPassword and save user

        EditText editTextOldPassword = findViewById(R.id.editTextOldPassword);
        if (editTextOldPassword.getText().toString().isEmpty()) {
            // save user without changing password
            updateUserWithoutPassword();
        } else {
            // check if editTextOldPassword is corrected and then compare editTextNewPassword and editTextConfirmPassword and save user
            updateUserWithPassword();
        }
    }

    private void updateUserWithoutPassword() {
        EditText name = findViewById(R.id.editTextName);
        EditText lastname = findViewById(R.id.editTextLastName);
        EditText email = findViewById(R.id.editTextEmail);
        EditText address = findViewById(R.id.editTextAddress);
        EditText phone = findViewById(R.id.editTextPhone);

        if (name.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || email.getText().toString().isEmpty() || address.getText().toString().isEmpty() || phone.getText().toString().isEmpty()) {
            // Display an error message using Snackbar
            Snackbar.make(findViewById(android.R.id.content), "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            // Display an error message using Snackbar
            Snackbar.make(findViewById(android.R.id.content), "Please enter a valid email address", Snackbar.LENGTH_SHORT).show();
            return;
        }

        userInfoDTO.setName(name.getText().toString());
        userInfoDTO.setLastname(lastname.getText().toString());
        userInfoDTO.setUsername(email.getText().toString());
        userInfoDTO.setAddress(address.getText().toString());
        userInfoDTO.setPhone(phone.getText().toString());

        Call<UserInfoDTO> call = ClientUtils.userService.updateUserInfo(userInfoDTO.getUserID(), userInfoDTO);
        call.enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (response.isSuccessful()) {
                    // Display a success message using Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "User updated successfully", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Display an error message using Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "Error updating user", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfoDTO> call, Throwable t) {
                // Display an error message using Snackbar
                Snackbar.make(findViewById(android.R.id.content), "Error updating user", Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUserWithPassword() {
        EditText name = findViewById(R.id.editTextName);
        EditText lastname = findViewById(R.id.editTextLastName);
        EditText email = findViewById(R.id.editTextEmail);
        EditText address = findViewById(R.id.editTextAddress);
        EditText phone = findViewById(R.id.editTextPhone);
        EditText editTextOldPassword = findViewById(R.id.editTextOldPassword);
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        if (name.getText().toString().isEmpty() || lastname.getText().toString().isEmpty() || email.getText().toString().isEmpty() || address.getText().toString().isEmpty() || phone.getText().toString().isEmpty() || editTextOldPassword.getText().toString().isEmpty() || editTextNewPassword.getText().toString().isEmpty() || editTextConfirmPassword.getText().toString().isEmpty()) {
            // Display an error message using Snackbar
            Snackbar.make(findViewById(android.R.id.content), "Please fill in all fields", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // check if email is valid
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            // Display an error message using Snackbar
            Snackbar.make(findViewById(android.R.id.content), "Please enter a valid email address", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // check if editTextNewPassword and editTextConfirmPassword are the same
        if (!editTextNewPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())) {
            // Display an error message using Snackbar
            Snackbar.make(findViewById(android.R.id.content), "Passwords do not match", Snackbar.LENGTH_SHORT).show();
            return;
        }

        // check if old password is correct
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setPassword(editTextOldPassword.getText().toString());
        userLoginDTO.setUsername(userInfoDTO.getUsername());
        Call<AuthResponse> callCheckPassword = ClientUtils.userService.login(userLoginDTO);
        callCheckPassword.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    // update user
                    updateUserWithNewPassword();
                } else {
                    // Display an error message using Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "Old password is incorrect", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                // Display an error message using Snackbar
                Snackbar.make(findViewById(android.R.id.content), "Error updating user", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserWithNewPassword() {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO();
        userUpdateDTO.setName(userInfoDTO.getName());
        userUpdateDTO.setLastname(userInfoDTO.getLastname());
        userUpdateDTO.setUsername(userInfoDTO.getUsername());
        userUpdateDTO.setAddress(userInfoDTO.getAddress());
        userUpdateDTO.setPhone(userInfoDTO.getPhone());
        EditText editTextNewPassword = findViewById(R.id.editTextNewPassword);
        userUpdateDTO.setPassword(editTextNewPassword.getText().toString());

        Call<UserInfoDTO> call = ClientUtils.userService.updateUserInfo(userInfoDTO.getUserID(), userUpdateDTO);
        call.enqueue(new Callback<UserInfoDTO>() {
            @Override
            public void onResponse(Call<UserInfoDTO> call, Response<UserInfoDTO> response) {
                if (response.isSuccessful()) {
                    // Display a success message using Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "User updated successfully", Snackbar.LENGTH_SHORT).show();
                } else {
                    // Display an error message using Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "Error updating user", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserInfoDTO> call, Throwable t) {
                // Display an error message using Snackbar
                Snackbar.make(findViewById(android.R.id.content), "Error updating user", Snackbar.LENGTH_SHORT).show();
            }
        });

        // set password fields to empty
        EditText editTextOldPassword = findViewById(R.id.editTextOldPassword);
        editTextOldPassword.setText("");
        EditText editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextConfirmPassword.setText("");
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextNewPassword.setText("");
    }
    // delete user
    private void deleteUser() {
        Call<Void> call = ClientUtils.userService.deleteAccount(userInfoDTO.getUserID());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Display a success message using Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "Successfully deleted user", Snackbar.LENGTH_SHORT).show();

                    // Clear token and username from SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();

                    // Clear UserInfoDTO from SharedViewModel
                    SharedViewModel sharedViewModel = new SharedViewModel();
                    sharedViewModel.setUserInfoDTO(null);

                    // logout and navigate to login activity
                    Intent intent = new Intent(EditProfileActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    finish();
                } else {
                    // Display an error message using Snackbar
                    Snackbar.make(findViewById(android.R.id.content), "Error deleting user", Snackbar.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Display an error message using Snackbar
                Snackbar.make(findViewById(android.R.id.content), "Error deleting user", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
