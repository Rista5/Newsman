package com.newsman.newsman.Auxiliary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Me on 1/10/2019.
 */

public class DateGetter {

    public static Date getDate(String date)throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        return sdf.parse(date);
    }

    public static Date parseDate(String dateString) {
        Date date = new Date();
        try{
            date = DateGetter.getDate(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
