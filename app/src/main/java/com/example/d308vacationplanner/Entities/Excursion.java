package com.example.d308vacationplanner.Entities;

import java.time.LocalDate;
import java.util.Date;

public class Excursion {
    private String title;
    private LocalDate date;

    public Excursion(String title, LocalDate date) {
        this.title = title;
        this.date = date;
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
