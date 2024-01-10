package com.example.bookingappteam17.services.review;

import com.example.bookingappteam17.model.review.ReportedReviewCardDTO;

import java.util.HashSet;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IReviewService {
    @GET("reportedReview/accommodation")
    Call<HashSet<ReportedReviewCardDTO>> getReportedReviews();

    @DELETE("reportedReview/{id}")
    Call<Void> deleteReport(@Path("id") Long id);

    @DELETE("review/" + "{hostPath}" + "{id}")
    Call<Void> deleteReview(@Path("id") Long id, @Path("hostPath") String hostPath);
}
