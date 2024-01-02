package com.example.bookingappteam17.activities.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookingappteam17.R;
import com.example.bookingappteam17.activities.home.HomeActivity;
import com.example.bookingappteam17.authentication.AuthenticationCallback;
import com.example.bookingappteam17.authentication.AuthenticationManager;
import com.example.bookingappteam17.clients.ClientUtils;
import com.example.bookingappteam17.services.user.IUserService;
import com.example.bookingappteam17.dto.user.UserLoginDTO;
import com.example.bookingappteam17.model.user.AuthResponse;

@RequiresApi(api = Build.VERSION_CODES.R)
public class LoginActivity extends AppCompatActivity {

    private boolean isPermissions = true;
    private final String [] permissions = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private static final int REQUEST_PERMISSIONS = 200;

    private IUserService userService = ClientUtils.userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button buttonLogin = findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(v -> handleLogin());

        Button buttonRegister = findViewById(R.id.registerButton);
        buttonRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
        onRequestPermission();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_PERMISSIONS:
                for(int i = 0; i < permissions.length; i++) {
                    Log.i("ShopApp", "permission " + permissions[i] + " " + grantResults[i]);
                    if(grantResults[i] == PackageManager.PERMISSION_DENIED){
                        isPermissions = false;
                    }
                }
                break;
        }

        if (!isPermissions) {
            Log.e("ShopApp", "Error: no permission");
            finishAndRemoveTask();
        }

    }

    private void onRequestPermission(){
        Log.i("ShopApp", "onRequestPermission");
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
                Log.i("ShopApp", "permission " + permission + " is denied");
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
                break;
            }
        }
    }

    private void handleLogin() {
        EditText editTextLoginEmailAddress = findViewById(R.id.editTextLoginEmailAddress);
        EditText editTextLoginPassword = findViewById(R.id.editTextLoginPassword);

        String email = editTextLoginEmailAddress.getText().toString();
        String password = editTextLoginPassword.getText().toString();

        // Validate email and password
        if (email.isEmpty()) {
            editTextLoginEmailAddress.setError("Email is required");
            editTextLoginEmailAddress.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editTextLoginPassword.setError("Password is required");
            editTextLoginPassword.requestFocus();
            return;
        }

        // get data from form and add to UserLoginDTO
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername(email);
        userLoginDTO.setPassword(password);

        // send data to server
        AuthenticationManager authenticationManager = new AuthenticationManager(userService, getApplicationContext());
        authenticationManager.loginUser(userLoginDTO, new AuthenticationCallback() {
            @Override
            public void onSuccess(AuthResponse token) {
                // Handle successful login, for example, navigate to the next screen
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String errorMessage) {
                // Handle login failure, show an error message, etc.
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}