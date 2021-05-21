package com.codepan.twinsrobo_apps.OtherClass;

import java.util.Date;

public class DateRange extends StringDateFormat{
    String rangeDateResult = "Tanggal Tidak Valid";

    public String getStringRangeDate(String tgl1, String tgl2){ // "2020-02-10" "12-april-2020"

//        String tgl1 = getStringDate(tanggal1);
//        String tgl2 = getStringDate(tanggal2);

        if (Integer.parseInt(tgl1.substring(0,4)) == Integer.parseInt(tgl2.substring(0,4))){
            if(Integer.parseInt(tgl1.substring(5,7)) == Integer.parseInt(tgl2.substring(5,7))){
                if(Integer.parseInt(tgl1.substring(8,10)) == Integer.parseInt(tgl2.substring(8,10))){
                    this.rangeDateResult = getStringDate2(tgl1);
                }
                else if(Integer.parseInt(tgl1.substring(8,10)) < Integer.parseInt(tgl2.substring(8,10))){
                    String subDate = getStringDate2(tgl1);
                    subDate = subDate.substring(0, 2);
                    this.rangeDateResult = subDate + " ~ " + getStringDate2(tgl2);
                }
            }
            else if (Integer.parseInt(tgl1.substring(5,7)) < Integer.parseInt(tgl2.substring(5,7))){
                String subDate = getStringDate2(tgl1);
                subDate = subDate.substring(0, subDate.length() - 5);
                this.rangeDateResult = subDate + " ~ " + getStringDate2(tgl2);
            }
        }
        else if (Integer.parseInt(tgl1.substring(0,4)) < Integer.parseInt(tgl2.substring(0,4))){
            this.rangeDateResult = getStringDate2(tgl1) + " ~ " + getStringDate2(tgl2);
        }

        return rangeDateResult;
    }

    public String getStringRangeDate(Date tanggal1, Date tanggal2){

        String tgl1 = getStringDate(tanggal1);
        String tgl2 = getStringDate(tanggal2);

        this.rangeDateResult = getStringRangeDate(tgl1, tgl2);
        return rangeDateResult;
    }
}
