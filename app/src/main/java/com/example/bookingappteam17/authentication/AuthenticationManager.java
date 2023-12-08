package com.example.bookingappteam17.authentication;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bookingappteam17.clients.IUserService;
import com.example.bookingappteam17.dto.UserLoginDTO;
import com.example.bookingappteam17.model.AuthResponse;
import com.example.bookingappteam17.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationManager {

    private IUserService userService;
    private SharedPreferences sharedPreferences;

    public AuthenticationManager(IUserService userService, Context context) {
        this.userService = userService;
        this.sharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
    }


    public void loginUser(UserLoginDTO userLoginDTO, final AuthenticationCallback callback) {
        Call<AuthResponse> call = userService.login(userLoginDTO);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    AuthResponse authenticatedUser = response.body();
                    saveAuthToken(authenticatedUser, userLoginDTO.getUsername()); // Save user data locally
                    callback.onSuccess(authenticatedUser);
                } else {
                    callback.onFailure("Login failed. Please check your credentials.");
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onFailure("Login failed. Please try again.");
            }
        });
    }

    private void saveAuthToken(AuthResponse token, String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token.getToken());
        editor.putString("username", username);

        editor.apply();
    }

}
