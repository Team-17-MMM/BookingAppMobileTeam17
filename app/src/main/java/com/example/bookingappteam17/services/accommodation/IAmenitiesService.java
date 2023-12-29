package com.example.bookingappteam17.services.accommodation;

import com.example.bookingappteam17.dto.accommodation.AccommodationDTO;

import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IAmenitiesService {
    @GET("amenity")
    Call<List<String>> getAllAmenities();
}
