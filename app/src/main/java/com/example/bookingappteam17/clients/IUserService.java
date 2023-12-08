package com.example.bookingappteam17.clients;

import com.example.bookingappteam17.dto.UserInfoDTO;
import com.example.bookingappteam17.dto.UserLoginDTO;
import com.example.bookingappteam17.model.AuthResponse;
import com.example.bookingappteam17.model.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("user/")
    Call<ArrayList<User>> getAll();

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("user/{id}")
    Call<User> getById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("user/")
    Call<User> add(@Body User user);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("user/{id}")
    Call<ResponseBody> deleteById(@Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("user/")
    Call<User> edit(@Body User user);
    
    //    TODO
    // vraca ti token, a token je string, moras ako je uspesno logovanje da
    // ponovo zoves servis i vuces podatke samo kao DTO bez sifre

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("/logIn")
    Call<AuthResponse> login(@Body UserLoginDTO userLoginDTO);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("user/userInfo/{username}")
    Call<UserInfoDTO> getUserInfo(@Path("username") String username);
}
