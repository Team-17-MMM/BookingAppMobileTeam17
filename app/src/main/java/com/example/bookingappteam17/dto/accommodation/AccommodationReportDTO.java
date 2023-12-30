package com.example.bookingappteam17.dto.accommodation;

public class AccommodationReportDTO {
    private Long id;
    private String name;
    private Double profit;
    private Integer numberOfReservations;

    public AccommodationReportDTO() {
    }

    public AccommodationReportDTO(Long id, String name, Double profit, Integer numberOfReservations) {
        this.id = id;
        this.name = name;
        this.profit = profit;
        this.numberOfReservations = numberOfReservations;
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Double getProfit() {
        return profit;
    }
    public Integer getNumberOfReservations() {
        return numberOfReservations;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setProfit(Double profit) {
        this.profit = profit;
    }
    public void setNumberOfReservations(Integer numberOfReservations) {
        this.numberOfReservations = numberOfReservations;
    }

}
