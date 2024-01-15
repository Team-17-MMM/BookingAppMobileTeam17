package com.example.bookingappteam17.services.reservation;

import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationReportDTO;
import com.example.bookingappteam17.dto.reservation.ReservationDTO;
import com.example.bookingappteam17.dto.reservation.ReservationFilterRequestDTO;
import com.example.bookingappteam17.dto.reservation.ReservationInfoDTO;
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
    Call<ReservationDTO> getReservation(@Path("id") Long id);
    @GET("reservation/user/{id}")
    Call<HashSet<ReservationDTO>> getUserReservations(@Path("id") Long id);


    @POST("reservation")
    Call<ReservationDTO> createReservation(@Body ReservationDTO reservation);

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

    @GET("reservation/info/user/{id}")
    Call<HashSet<ReservationInfoDTO>> getUserInfoReservations(@Path("id") Long id);
    @GET("reservation/host/{id}")
    Call<HashSet<ReservationInfoDTO>> getHostReservations(@Path("id") Long id);

    @GET("reservation/DTO/host/{id}")
    Call<HashSet<ReservationDTO>> getHostReservationDTOs(@Path("id") Long id);
    @PUT("reservation/accept/{id}")
    Call<HashSet<ReservationDTO>> acceptReservation(@Body Long resId, @Path("id") Long id);
    @PUT("reservation/cancel/{id}")
    Call<ReservationDTO> cancelReservation(@Body Long resId, @Path("id") Long id);
    @PUT("reservation/reject/{id}")
    Call<ReservationDTO> rejectReservation(@Body Long resId, @Path("id") Long id);

    @POST("reservation/search/info")
    Call<HashSet<ReservationInfoDTO>> filterReservationsFromList(@Body ReservationFilterRequestDTO reservationRequest);

    @GET("reservation/accommodations/info/{id}")
    Call<HashSet<AccommodationDTO>> getAccommodationsInfo(@Path("id") Long id);

}
