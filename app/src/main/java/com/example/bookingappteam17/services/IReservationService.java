package com.example.bookingappteam17.services;

import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.reservation.ReservationPostDTO;
import com.example.bookingappteam17.model.Reservation;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IReservationService {
    @GET("reservation")
    Call<HashSet<Reservation>> getReservations();

    @GET("reservation/{id}")
    Call<Reservation> getReservation(@Path("id") Long id);
    @GET("reservation/user/{id}")
    Call<HashSet<Reservation>> getUserReservations(@Path("id") Long id);


    @POST("reservation")
    Call<Reservation> createReservation(@Body ReservationPostDTO reservation);

    @PUT("reservation/{id}")
    Call<Reservation> updateReservation(@Body Reservation reservation, @Path("id") Long id);

    @DELETE("reservation/{id}")
    Call<Reservation> deleteReservation(@Path("id") Long id);
}
