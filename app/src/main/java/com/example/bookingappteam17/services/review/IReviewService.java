package com.example.bookingappteam17.services.review;

import com.example.bookingappteam17.dto.review.HostReviewDTO;
import com.example.bookingappteam17.dto.review.ReportedReviewDTO;
import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;

import java.util.HashSet;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface IReviewService {
    @GET("reportedReview/accommodation")
    Call<HashSet<ReportedReviewCardDTO>> getReportedReviews();

    @DELETE("reportedReview/{id}")
    Call<Void> deleteReport(@Path("id") Long id);

    @DELETE
    Call<Void> deleteReview(@Url String path);

    @GET("review/host/user/{email}")
    Call<HashSet<HostReviewDTO>> getHostReviews(@Path("email") String email);

    @POST("reportedReview")
    Call<ReportedReviewDTO> reportHostReview(@Body ReportedReviewDTO reportedReviewDTO);

    @POST("review/host")
    Call<Void> submitHostReview(@Body HostReviewDTO review);
}