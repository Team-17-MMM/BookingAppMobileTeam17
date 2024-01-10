package com.example.bookingappteam17.dto.review;

import java.time.LocalDate;

public class HostReviewDTO{

    private Long reviewID;
    private String comment;
    private int rating;
    private Long reviewer;
    private Long reviewedHost;
    private String reviewDate;

    public HostReviewDTO(Long reviewID, String comment, int rating, Long reviewer, Long reviewedHost, String reviewDate) {
        this.reviewID = reviewID;
        this.comment = comment;
        this.rating = rating;
        this.reviewer = reviewer;
        this.reviewedHost = reviewedHost;
        this.reviewDate = reviewDate;
    }

    public HostReviewDTO(){}

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getReviewer() {
        return reviewer;
    }

    public void setReviewer(Long reviewer) {
        this.reviewer = reviewer;
    }

    public Long getReviewedHost() {
        return reviewedHost;
    }

    public void setReviewedHost(Long reviewedHost) {
        this.reviewedHost = reviewedHost;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
