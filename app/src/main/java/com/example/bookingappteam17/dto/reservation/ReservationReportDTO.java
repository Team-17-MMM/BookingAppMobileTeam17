package com.example.bookingappteam17.dto.reservation;

import java.util.Map;
import java.util.Set;

public class ReservationReportDTO {
    private Map<String, Double> pricesMap;
    private Map<String, Integer> numberOfReservationsMap;

    public ReservationReportDTO() {
    }

    public ReservationReportDTO(Map<String, Double> pricesMap, Map<String, Integer> numberOfReservationsMap) {
        this.pricesMap = pricesMap;
        this.numberOfReservationsMap = numberOfReservationsMap;
    }

    public Map<String, Double> getPricesMap() {
        return pricesMap;
    }

    public void setPricesMap(Map<String, Double> pricesMap) {
        this.pricesMap = pricesMap;
    }

    public Map<String, Integer> getNumberOfReservationsMap() {
        return numberOfReservationsMap;
    }

    public void setNumberOfReservationsMap(Map<String, Integer> numberOfReservationsMap) {
        this.numberOfReservationsMap = numberOfReservationsMap;
    }
}
