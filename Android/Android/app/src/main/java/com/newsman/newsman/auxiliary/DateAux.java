package com.newsman.newsman.auxiliary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Me on 1/10/2019.
 */

public class DateAux {
    public static final String PRINT_DATE_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    public static Date getDate(String date)throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat(INPUT_DATE_FORMAT);
        return sdf.parse(date);
    }

    public static Date parseDate(String dateString) {
        Date date = new Date();
        try{
            date = DateAux.getDate(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(PRINT_DATE_FORMAT);
        return sdf.format(date);
    }
}
