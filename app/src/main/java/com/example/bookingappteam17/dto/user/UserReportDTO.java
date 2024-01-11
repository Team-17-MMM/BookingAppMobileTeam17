package com.example.bookingappteam17.dto.user;

public class UserReportDTO {
    private Long id;
    private Long userID;
    private String reportedUser;
    private String description;
    private boolean banned;

    public UserReportDTO(Long id, Long userID, String reportedUser, String description, boolean banned) {
        this.id = id;
        this.userID = userID;
        this.reportedUser = reportedUser;
        this.description = description;
        this.banned = banned;
    }

    public UserReportDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(String reportedUser) {
        this.reportedUser = reportedUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

}
