package com.example.d308vacationplanner.Entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Vacations")
public class Vacation {
    @PrimaryKey(autoGenerate = true)
    private long vacationId;
    private String title;
    private String hotel;
    private Date startDate;
    private Date endDate;


    public Vacation(long vacationId, String title, String hotel, Date startDate, Date endDate) {
        this.vacationId = vacationId;
        this.title = title;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getVacationId() {
        return vacationId;
    }

    public String toString() {
        return title;
    }

    public void setVacationId(long vacationId) {
        this.vacationId = vacationId;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
