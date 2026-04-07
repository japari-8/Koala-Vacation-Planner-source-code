package com.example.d308vacationplanner.Entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Date;

@Entity(tableName = "Excursions", foreignKeys = @ForeignKey(entity = Vacation.class, parentColumns = "vacationId", childColumns = "vacationId"))
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    private long excursionId;
    private String title;
    private LocalDate date;
    private long vacationId;

    public Excursion(long excursionId, String title, LocalDate date, long vacationId) {
        this.excursionId = excursionId;
        this.title = title;
        this.date = date;
        this.vacationId = vacationId;
    }

    public long getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(long excursionId) {
        this.excursionId = excursionId;
    }

    public long getVacationId() {
        return vacationId;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
