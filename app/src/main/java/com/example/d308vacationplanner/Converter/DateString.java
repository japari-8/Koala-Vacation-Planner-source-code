package com.example.d308vacationplanner.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateString {
    private Date date;
    private String st;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    public String dateToString(Date date) {

        st = sdf.format(date);
        return st;
    }

    public Date stringToDate (String st) throws ParseException {

        sdf.setLenient(false);
        date = sdf.parse(st);
        return  date;
    }

}
