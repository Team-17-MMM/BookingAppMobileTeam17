package com.example.bookingappteam17.services.accommodation;


import com.example.bookingappteam17.dto.accommodation.AccommodationCardDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationCardRDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;
import com.example.bookingappteam17.dto.accommodation.AccommodationUpdateDTO;
import com.example.bookingappteam17.dto.accommodation.AvailabilityPeriodDTO;
import com.example.bookingappteam17.dto.accommodation.AvailabilityPeriodRangeDTO;

import java.util.HashSet;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAccommodationService {

    @GET("accommodation")
    Call<HashSet<AccommodationDTO>> getAccommodations();

    @GET("accommodation/{id}")
    Call<AccommodationDTO> getAccommodation(@Path("id") Long id);

    @POST("accommodation")
    Call<AccommodationDTO> createAccommodation(@Body AccommodationDTO accommodationDTO);

    @PUT("accommodation/{id}")
    Call<AccommodationUpdateDTO> updateAccommodation(@Body AccommodationDTO accommodationDTO, @Path("id") Long id);

    @PUT("accommodation/{id}/availabilityPeriods")
    Call<AccommodationDTO> updateAccommodationAvailabilityPeriods(@Body List<AvailabilityPeriodDTO> availabilityPeriods, @Path("id") Long id);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("accommodation/{id}/period")
    Call<AccommodationDTO> updateAccommodationPeriod(@Body AvailabilityPeriodRangeDTO availabilityPeriodRangeDTO, @Path("id") Long id);

    @DELETE("accommodation/{id}")
    Call<AccommodationDTO> deleteAccommodation(@Path("id") Long id);

    @GET("accommodation/cardsI")
    Call<HashSet<AccommodationCardRDTO>> getAccommodationsCards();

    @GET("accommodation/cardsI/{username}")
    Call<HashSet<AccommodationCardRDTO>> getHostAccommodationsCards(@Path("username") String username);

    @GET("accommodation/host/{id}")
    Call<HashSet<Long>> getHostAccommodationIDs(@Path("id") Long id);

    @GET("accommodation/search")
    Call<HashSet<AccommodationCardDTO>> searchAccommodations(
            @Query("searchLocation") String searchLocation,
            @Query("startDate") String startDate,
            @Query("endDate") String endDate,
            @Query("persons") Long persons,
            @Query("minPrice") Long minPrice,
            @Query("maxPrice") Long maxPrice,
            @Query("amenities") List<String> amenities,
            @Query("accommodationTypes") List<String> accommodationTypes
    );

    @Multipart
    @POST("accommodation/{Id}/picture")
    Call<Void> uploadAccommodationPicture(@Path("Id") Long Id, @Part MultipartBody.Part image);

    @GET("accommodation/{Id}/picture")
    Call<ResponseBody> getAccommodationImage(@Path("Id") Long Id);

    @GET("amenity")
    Call<List<String>> getAllAmenities();

    @GET("accommodationType")
    Call<List<String>> getAllAccommodationTypes();

    @GET("accommodation/notApproved")
    Call<HashSet<AccommodationCardRDTO>> getNotApprovedAccommodations();

    @PUT("accommodation/approveAccommodation/{id}")
    Call<AccommodationDTO> setApproveAccommodation(@Path("id") Long id, @Body Boolean approved);

    @PUT("accommodation/updateByNewAccommodation/{id}")
    Call<AccommodationDTO> updateByNewAccommodation(@Path("id") Long id);
}
