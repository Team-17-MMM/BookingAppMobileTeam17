package com.example.bookingappteam17.dto.review;

public class AccommodationReviewDTO {
    private Long reviewID;
    private Long accommodationID;
    private Long userID;
    private String comment;
    private int grade;
    private String reviewDate;

    private boolean approved;

    public String getReviewDate() {
        return reviewDate;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Long getReviewID() {
        return reviewID;
    }

    public void setReviewID(Long reviewID) {
        this.reviewID = reviewID;
    }

    public Long getAccommodationID() {
        return accommodationID;
    }

    public void setAccommodationID(Long accommodationID) {
        this.accommodationID = accommodationID;
    }

    public Long getReviewer() {
        return userID;
    }

    public void setReviewer(Long userID) {
        this.userID = userID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public AccommodationReviewDTO() {
    }

    public AccommodationReviewDTO(Long reviewID, boolean approved, Long accommodationID, Long userID, String comment, int rating, String reviewDate) {
        this.reviewID = reviewID;
        this.accommodationID = accommodationID;
        this.userID = userID;
        this.comment = comment;
        this.grade = rating;
        this.reviewDate = reviewDate;
        this.approved = approved;
    }
}