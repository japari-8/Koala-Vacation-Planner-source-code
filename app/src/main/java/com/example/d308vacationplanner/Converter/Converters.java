package com.example.d308vacationplanner.Converter;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long date) {

        return date == null ? null : new Date(date);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {

        return date == null ? null : date.getTime();
    }
}
