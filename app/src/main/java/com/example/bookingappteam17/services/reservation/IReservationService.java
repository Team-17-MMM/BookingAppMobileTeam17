package com.example.bookingappteam17.services.reservation;

import com.example.bookingappteam17.dto.accommodation.AccommodationReportDTO;
import com.example.bookingappteam17.dto.reservation.ReservationPostDTO;
import com.example.bookingappteam17.dto.reservation.ReservationReportDTO;
import com.example.bookingappteam17.model.reservation.Reservation;

import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

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

    @GET("reservation/report/{id}")
    Call<ReservationReportDTO> getReport(@Path("id") Long id);
    @GET("reservation/report")
    Call<List<AccommodationReportDTO>> getHostReport(
            @Query("username") String username,
            @Query("start") String start,
            @Query("end") String end
    );
}
