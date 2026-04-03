package com.example.d308vacationplanner.Entities;

import java.time.LocalDate;
import java.util.Date;

public class Vacation {
    private String title;
    private String hotel;
    private LocalDate startDate;
    private LocalDate endDate;

    public Vacation(String title, String hotel, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
