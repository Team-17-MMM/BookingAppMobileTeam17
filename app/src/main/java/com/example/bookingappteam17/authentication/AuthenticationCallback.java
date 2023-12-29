package com.example.bookingappteam17.authentication;

import com.example.bookingappteam17.model.user.AuthResponse;

public interface AuthenticationCallback {
    void onSuccess(AuthResponse token);

    void onFailure(String errorMessage);
}
