package com.codepan.twinsrobo_apps.OtherClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringDateFormat {
    String stringDate;

    public String getStringDate(String tanggal){
        Date tgl = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        try {
            tgl = format.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateToString = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        stringDate = dateToString.format(tgl);

        return dateToString.format(tgl);
    }

    public String getStringDate(Date tanggal){
        SimpleDateFormat dateToString = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        stringDate = dateToString.format(tanggal);

        return dateToString.format(tanggal);
    }

    public String getStringDate2(String tanggal){
        Date tgl = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            tgl = format.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat dateToString = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        stringDate = dateToString.format(tgl);

        return dateToString.format(tgl);
    }

    public String getStringDate2(Date tanggal){
        SimpleDateFormat dateToString = new SimpleDateFormat("dd-MMMM-yyyy", Locale.US);
        stringDate = dateToString.format(tanggal);

        return dateToString.format(tanggal);
    }
}
