package com.example.bookingappteam17.clients;

import com.example.bookingappteam17.BuildConfig;
import com.example.bookingappteam17.services.accommodation.IAccommodationService;
import com.example.bookingappteam17.services.accommodation.IAmenitiesService;
import com.example.bookingappteam17.services.reservation.IReservationService;
import com.example.bookingappteam17.services.user.IUserService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientUtils {
    public static final String SERVICE_API_PATH = "http://"+ BuildConfig.IP_ADDR +":8050/";
//public static final String SERVICE_API_PATH = "http://localhost:8050/";

    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    public static IUserService userService = retrofit.create(IUserService.class);
    public static IAccommodationService accommodationService = retrofit.create(IAccommodationService.class);
    public static IReservationService reservationService = retrofit.create(IReservationService.class);
    public static IAmenitiesService amenitiesService = retrofit.create(IAmenitiesService.class);
}
