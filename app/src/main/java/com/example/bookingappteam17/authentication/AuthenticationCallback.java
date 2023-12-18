package com.example.bookingappteam17.authentication;

import com.example.bookingappteam17.model.AuthResponse;
import com.example.bookingappteam17.model.User;

public interface AuthenticationCallback {
    void onSuccess(AuthResponse token);

    void onFailure(String errorMessage);
}
