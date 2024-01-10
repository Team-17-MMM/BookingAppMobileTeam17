package com.example.bookingappteam17.dto.review;


public class ReportedReviewDTO {
    private Long reportedReviewID;
    private boolean approved;
    private Long reviewID;
    private boolean hostReview;

    public ReportedReviewDTO(){}

    public ReportedReviewDTO(Long reportedReviewID, boolean approved, Long reviewID, boolean hostReview) {
        this.reportedReviewID = reportedReviewID;
        this.approved = approved;
        this.reviewID = reviewID;
        this.hostReview = hostReview;
    }

    public Long getReportedReviewID() {
        return reportedReviewID;
    }

    public void setReportedReviewID(Long reportedReviewID) {
        this.reportedReviewID = reportedReviewID;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public boolean isHostReview() {
        return hostReview;
    }

    public void setHostReview(boolean hostReview) {
        this.hostReview = hostReview;
    }}
